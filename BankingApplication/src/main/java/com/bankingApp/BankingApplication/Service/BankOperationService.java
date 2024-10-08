package com.bankingApp.BankingApplication.Service;

import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface BankOperationService {
    ResponseEntity<?> withdraw(List<CustomerAccount> customerAccount,float amount);
}
