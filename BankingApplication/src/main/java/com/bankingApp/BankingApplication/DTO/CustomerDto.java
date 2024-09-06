package com.bankingApp.BankingApplication.DTO;

import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    String address;

    @NotNull
    String country;

    @NotNull
    String state;

    @NotNull
    int pinCode;

    String gender;

    @NotNull
    Long accountNumber;

    @NotNull
    @Email(message = "Enter a valid email.")
    String email;

    @NotNull
    Long phoneNumber;

    @NotNull
    Long alternativePhone;

}
