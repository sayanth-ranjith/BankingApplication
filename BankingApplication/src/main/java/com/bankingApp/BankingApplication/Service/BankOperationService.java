package com.bankingApp.BankingApplication.Service;

import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public interface BankOperationService {
    ResponseEntity<?> withdraw(List<CustomerAccount> customerAccount, float amount, ReentrantLock lock);

    ResponseEntity<?> deposit(List<CustomerAccount> customerAccount, float amount);

    ResponseEntity<?> transferMoney(List<CustomerAccount> fromAccountDetails,List<CustomerAccount> toAccountDetails,float amount);
}
