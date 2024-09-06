package com.bankingApp.BankingApplication.Service;

import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountDetails extends JpaRepository<CustomerAccount,String> {

    List<CustomerAccount> findAllByAccountNumber (String accountNumber);


}