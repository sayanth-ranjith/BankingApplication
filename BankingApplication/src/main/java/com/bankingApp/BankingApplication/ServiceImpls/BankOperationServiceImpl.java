package com.bankingApp.BankingApplication.ServiceImpls;

import com.bankingApp.BankingApplication.Controllers.BankOperationController;
import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import com.bankingApp.BankingApplication.Entity.TransactionHistory;
import com.bankingApp.BankingApplication.ExceptionHandling.InsufficientFundException;
import com.bankingApp.BankingApplication.Service.BankAccountDetails;
import com.bankingApp.BankingApplication.Service.BankOperationService;
import com.bankingApp.BankingApplication.Service.TransactionHistoryTableService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class BankOperationServiceImpl implements BankOperationService {
    @Autowired
    BankAccountDetails bankAccountDetails;

    @Autowired
    TransactionHistoryTableService transactionHistoryTableService;

    Logger logger = LoggerFactory.getLogger(BankOperationController.class);

    private static final String withdrawal ="WITHDRAWAL";
    private static final String deposit = "DEPOSIT";

    @Override
    @Transactional
    public synchronized ResponseEntity<?> withdraw(List<CustomerAccount> customerAccount,float requestedAmount) {
        for(CustomerAccount customer : customerAccount){
            String accountBalance = customer.getAccountBalance();
            float currentBalance = Float.parseFloat(accountBalance);
            if(requestedAmount > currentBalance){
                ResponseEntity<?> respEntity=BankOperationController.saveTransactionHistory(customer,"FAILURE",withdrawal,String.valueOf(requestedAmount),"self",transactionHistoryTableService);
                logger.info(Objects.requireNonNull(respEntity.getBody()).toString());
                throw new InsufficientFundException("Not allowed");
            }
            float newBalance = currentBalance - requestedAmount;
            //TODO:Update the new balance in db.
            customer.setAccountBalance(Float.toString(newBalance));
            bankAccountDetails.save(customer);
            ResponseEntity<?> respEntity = BankOperationController.saveTransactionHistory(customer, "SUCCESS", withdrawal, String.valueOf(requestedAmount), "self", transactionHistoryTableService);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deposit(List<CustomerAccount> customerAccount, float amount) {
        String currentBalance = customerAccount.get(0).getAccountBalance();
        float balance = Float.parseFloat(currentBalance);
        float newBalance = balance + amount;
        customerAccount.get(0).setAccountBalance(String.valueOf(newBalance));
        bankAccountDetails.save(customerAccount.get(0));
        BankOperationController.saveTransactionHistory(customerAccount.get(0), "SUCCESS", deposit, String.valueOf(amount), "self", transactionHistoryTableService);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
