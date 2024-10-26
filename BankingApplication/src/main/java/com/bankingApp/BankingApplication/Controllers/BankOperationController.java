package com.bankingApp.BankingApplication.Controllers;

import com.bankingApp.BankingApplication.DTO.CreateCustomer;
import com.bankingApp.BankingApplication.DTO.TransferRequest;
import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import com.bankingApp.BankingApplication.Entity.TransactionHistory;
import com.bankingApp.BankingApplication.ExceptionHandling.AccountNotFoundException;
import com.bankingApp.BankingApplication.ExceptionHandling.InsufficientFundException;
import com.bankingApp.BankingApplication.Service.BankAccountDetails;
import com.bankingApp.BankingApplication.Service.BankOperationService;
import com.bankingApp.BankingApplication.Service.TransactionHistoryTableService;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This will be a major controller.
 * Bank-Accounts would be created,deleted and if required updated from this controller.
 * Usually updation is unavailable so updation should be available only if user is an admin.
 */
@RestController
@RequestMapping("/api/v1")
public class BankOperationController {

    @Autowired
    BankAccountDetails bankAccountDetails;

    @Autowired
    TransactionHistoryTableService transactionHistoryTableService;

    Logger logger = LoggerFactory.getLogger(BankOperationController.class);

    private static final String withdrawal ="WITHDRAWAL";
    private static final String deposit = "DEPOSIT";

    @Autowired
    BankOperationService bankOperationService;



    @PostMapping("/createAccount")
    public ResponseEntity<?> createBankAccount(@RequestBody @Valid CreateCustomer customer){
        CustomerAccount account = new CustomerAccount();
        account.setFirstName(customer.getFirstName());
        account.setLastName(customer.getLastName());
        account.setPhoneNumber(customer.getPhoneNumber());
        account.setAlternativePhone(customer.getAlternativePhone());
        account.setAddress(customer.getAddress());
        account.setCountry(customer.getCountry());
        account.setAccountBalance(customer.getAccountBalance());
        account.setAccountNumber(customer.getAccountNumber());
        account.setTypeOfAccount(customer.getTypeOfAccount());

        //check if the account number already exists.
        List<CustomerAccount> accountDetails = getAccountDetailsByAccountNumber(customer.getAccountNumber());
        if(accountDetails.size()>0){
            //global exception
            return new ResponseEntity<>("Account ID already exists",HttpStatus.ALREADY_REPORTED);
        }

        bankAccountDetails.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getDetails/{accountNumber}")
    public ResponseEntity<?> getDetails(@PathVariable String accountNumber) {
        try {
            List<CustomerAccount> accountDetails = getAccountDetailsByAccountNumber(accountNumber);
            logger.info(accountDetails.get(0).getTransactionHistory().toString());
            if(accountDetails.size()==0){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(accountDetails.get(0),HttpStatus.OK);
        }catch (Exception e){
            //throw new BankingException("Cannot fetch the account.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //TODO API for withdrawing cash
    @PostMapping("/withDrawals/{accountNumber}/{requestedAmount}")
    public ResponseEntity<?> withDrawCash(@PathVariable String accountNumber,@PathVariable  float requestedAmount){
        List<CustomerAccount> customerDetail = getAccountDetailsByAccountNumber(accountNumber);
        if(customerDetail.isEmpty()){
            throw new AccountNotFoundException("Not found");
        }
        logger.info("Processing withdrawal request for account number " + accountNumber);
        return bankOperationService.withdraw(customerDetail,requestedAmount);

    }

    @Transactional
    public List<CustomerAccount> getAccountDetailsByAccountNumber(String accountNumber){
        List<CustomerAccount> details= bankAccountDetails.findAllByAccountNumber(accountNumber);
        Hibernate.initialize(details.get(0).getTransactionHistory()); // Force initialization

        //logger.info(details.get(0).toString());
        return details;
    }



    //TODO: API for depositing cash
    @PostMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<?> depositCash(@PathVariable String accountNumber,@PathVariable float amount){
        List<CustomerAccount> account = getAccountDetailsByAccountNumber(accountNumber);
        if(account.isEmpty()){
            throw new AccountNotFoundException("Not found");
        }
        return bankOperationService.deposit(account, amount);
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest transferRequest){
        logger.info("Request received for transfering money  "+ transferRequest);
        List<CustomerAccount> fromAccountDetails = getAccountDetailsByAccountNumber(transferRequest.fromAccount());
        List<CustomerAccount> toAccountDetails = getAccountDetailsByAccountNumber(transferRequest.toAccount());
        if(fromAccountDetails.isEmpty() || toAccountDetails.isEmpty()){
            logger.info("Bad request, one probably receiver or senders account number is invalid.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bankOperationService.transferMoney(fromAccountDetails,toAccountDetails,transferRequest.amount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<?> saveTransactionHistory(CustomerAccount customer,String status,String transactionType,String requestedAmount,String toAccount,TransactionHistoryTableService transactionHistoryTableService){
        TransactionHistory history = new TransactionHistory();
        UUID id = UUID.randomUUID();
        history.setTransactionId(id.toString());
        history.setCustomerAccount(customer);
        history.setTransactionType(transactionType);
        history.setAmount(requestedAmount);
        history.setBalanceAfter(customer.getAccountBalance());
        history.setStatus(status);
        history.setFromAccount(customer.getAccountNumber());
        history.setToAccount(toAccount);
        transactionHistoryTableService.save(history);

        return new ResponseEntity<>("Saved into transaction history table",HttpStatus.OK);
    }

    //TODO:API to check balance
}
