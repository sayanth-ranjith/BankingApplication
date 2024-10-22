package com.bankingApp.BankingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@ToString(exclude = "transactionHistory")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_account")
public class CustomerAccount {

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "alternative_phone", nullable = false)
    private String alternativePhone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "account_balance", nullable = false)
    private String accountBalance;

    @Column(name = "type_of_account", nullable = false)
    private String typeOfAccount;

    @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TransactionHistory> transactionHistory;

}
