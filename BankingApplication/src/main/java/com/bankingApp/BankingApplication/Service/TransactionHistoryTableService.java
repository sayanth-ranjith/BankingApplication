package com.bankingApp.BankingApplication.Service;

import com.bankingApp.BankingApplication.Entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TransactionHistoryTableService extends JpaRepository<TransactionHistory,String> {
}
