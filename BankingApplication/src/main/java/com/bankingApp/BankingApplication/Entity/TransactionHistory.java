package com.bankingApp.BankingApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private String amount;

    @Column(name = "balance_after")
    private String balanceAfter;

//    @Column(name="status")
//    private String status;

    @Column(name="from_account_id")
    private String fromAccount;

    @Column(name = "to_account_id")
    private String toAccount;

    @Column(name = "date_and_time")  // Creation timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;

}
