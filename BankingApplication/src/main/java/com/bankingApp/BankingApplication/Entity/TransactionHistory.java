package com.bankingApp.BankingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transaction_history_table")
@Data
@ToString(exclude = "customerAccount")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number", nullable = false)
    private CustomerAccount customerAccount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private String amount;

    @Column(name = "balance_after")
    private String balanceAfter;

    @Column(name="status")
    private String status;

    @Column(name="from_account_id")
    private String fromAccount;

    @Column(name = "to_account_id")
    private String toAccount;

    @Column(name = "date_and_time")  // Creation timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;

}
