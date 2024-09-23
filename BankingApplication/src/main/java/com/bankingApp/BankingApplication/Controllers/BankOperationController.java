package com.bankingApp.BankingApplication.Controllers;

import com.bankingApp.BankingApplication.DTO.CreateCustomer;
import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import com.bankingApp.BankingApplication.ExceptionHandling.AccountNotFoundException;
import com.bankingApp.BankingApplication.ExceptionHandling.InsufficientFundException;
import com.bankingApp.BankingApplication.Service.BankAccountDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This will be a major controller.
 * Bank-Accounts would be created,deleted and if required updated from this controller.
 * Usually updation is unavailable so updation should be available only if user is an admin.
 */
@RestController
@RequestMapping("/bank")
public class BankOperationController {

    @Autowired
    BankAccountDetails bankAccountDetails;

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
        for(CustomerAccount customer : customerDetail){
            String accountBalance = customer.getAccountBalance();
            float currentBalance = Float.parseFloat(accountBalance);
            if(requestedAmount > currentBalance){
                throw new InsufficientFundException("Not allowed");
            }
            float newBalance = currentBalance - requestedAmount;
            //TODO:Update the new balance in db.
            customer.setAccountBalance(Float.toString(newBalance));
            bankAccountDetails.save(customer);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public List<CustomerAccount> getAccountDetailsByAccountNumber(String accountNumber){
        return bankAccountDetails.findAllByAccountNumber(accountNumber);
    }



    //TODO: API for depositing cash
    @GetMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<?> depositCash(@PathVariable String accountNumber,@PathVariable float amount){
        List<CustomerAccount> account = getAccountDetailsByAccountNumber(accountNumber);
        if(account.isEmpty()){
            throw new AccountNotFoundException("Not found");
        }
        String currentBalance = account.get(0).getAccountBalance();
        float balance = Float.parseFloat(currentBalance);
        float newBalance = balance + amount;
        account.get(0).setAccountBalance(String.valueOf(newBalance));
        bankAccountDetails.save(account.get(0));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO:API to check balance
}
