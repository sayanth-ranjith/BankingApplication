package com.bankingApp.BankingApplication.ServiceImpls;

import com.bankingApp.BankingApplication.Controllers.BankOperationController;
import com.bankingApp.BankingApplication.Entity.CustomerAccount;
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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class BankOperationServiceImpl implements BankOperationService {
    @Autowired
    BankAccountDetails bankAccountDetails;

    @Autowired
    TransactionHistoryTableService transactionHistoryTableService;

    Logger logger = LoggerFactory.getLogger(BankOperationController.class);

    private static final String withdrawal ="WITHDRAWAL";
    private static final String deposit = "DEPOSIT";

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    @Transactional
    public ResponseEntity<?> withdraw(List<CustomerAccount> customerAccount,float requestedAmount,ReentrantLock lock) {
        try {
            lock.lock();
            for (CustomerAccount customer : customerAccount) {
                String accountBalance = customer.getAccountBalance();
                float currentBalance = Float.parseFloat(accountBalance);
                if (requestedAmount > currentBalance) {
                    ResponseEntity<?> respEntity = BankOperationController.saveTransactionHistory(customer, "FAILURE", withdrawal, String.valueOf(requestedAmount), "self", transactionHistoryTableService);
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
        }catch (Exception e){
            logger.info("Some exception occured while withdrawing money " + e + "lock released by thread " + Thread.currentThread().getName());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public synchronized ResponseEntity<?> deposit(List<CustomerAccount> customerAccount, float amount) {
        String currentBalance = customerAccount.get(0).getAccountBalance();
        float balance = Float.parseFloat(currentBalance);
        float newBalance = balance + amount;
        customerAccount.get(0).setAccountBalance(String.valueOf(newBalance));
        bankAccountDetails.save(customerAccount.get(0));
        BankOperationController.saveTransactionHistory(customerAccount.get(0), "SUCCESS", deposit, String.valueOf(amount), "self", transactionHistoryTableService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> transferMoney(List<CustomerAccount> fromAccountDetails, List<CustomerAccount> toAccountDetails, float amount) {
        ReentrantLock lock = new ReentrantLock();
        try {
            var withDrawMsg = "Transfer of %s from %s to %s is processing..."
                    .formatted(amount, fromAccountDetails.get(0).getAccountNumber(), toAccountDetails.get(0).getAccountNumber());
            logger.info(withDrawMsg);
            this.withdraw(fromAccountDetails, amount,lock);

            var depositMsg = "Depositing money into the receiver %s account during transfer...."
                    .formatted(toAccountDetails.get(0).getAccountNumber());
            this.deposit(toAccountDetails, amount);

            var finalMsg = "Transfer successful from %s to %s".formatted(fromAccountDetails.get(0).getAccountNumber(), toAccountDetails.get(0).getAccountNumber());
            logger.info(finalMsg);

            //TODO: add history,transaction histroy.
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Something went wrong during transaction from "+ fromAccountDetails.get(0).getAccountNumber() + "to " +fromAccountDetails.get(0).getAccountNumber());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
