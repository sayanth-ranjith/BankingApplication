package com.bankingApp.BankingApplication.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomer {
    @NotNull
    public String firstName;

    @NotNull
    public String lastName;

    @NotNull
    @Size(min = 10, max=10,message = "Phone number must contain 10 digits.")
    public String phoneNumber;

    @NotNull
    @Size(min = 10, max=10,message = "Phone number must contain 10 digits.")
    public String alternativePhone;

    @NotNull
    public String address;

    @NotNull
    public String country;

    @NotNull
    public String accountNumber;

    @NotNull
    public String accountBalance;

    @NotNull
    public String typeOfAccount;


}
