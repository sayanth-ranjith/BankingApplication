package com.bankingApp.BankingApplication.Entity;


import jakarta.persistence.*;
import lombok.Data;

/*
* This class maps to the table cpd_bank_tbl
* It stores the general information about the customers
* */

@Data
@Entity
@Table(name="cpd_bank_tbl")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="firstname" , nullable = false)
    String firstName;

    @Column(name="lastname" , nullable = false)
    String lastName;

    @Column(name="address" , nullable = false)
    String address;

    @Column(name="country",nullable = false)
    String country;

    @Column(name="state",nullable = false)
    String state;

    @Column(name="pincode" , nullable = false)
    int pinCode;

    @Column(name="gender" , nullable = true)
    String gender;

    @Column(name="accountnumber" , nullable = false)
    Long accountNumber;

    @Column(name="email",nullable = false)
    String email;

    @Column(name="phonenumber" , nullable = false)
    Long phoneNumber;

    @Column(name="alternativephone" , nullable = true)
    Long alternativePhone;

}
