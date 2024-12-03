package com.bankingApp.BankingApplication.Service;

import com.bankingApp.BankingApplication.Entity.AuthenticationTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepoService extends JpaRepository<AuthenticationTable,String> {
    AuthenticationTable findByEmail(String email);
}
