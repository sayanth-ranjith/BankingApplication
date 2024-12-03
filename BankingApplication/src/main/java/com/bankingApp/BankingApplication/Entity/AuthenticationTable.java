package com.bankingApp.BankingApplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name="authentication")
public class AuthenticationTable {

    @Id
    @Column(name="email" , nullable = false)
    private String email;

    @Column(name="password" , nullable = false)
    private String password;
}
