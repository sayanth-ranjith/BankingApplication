package com.bankingApp.BankingApplication.DTO;

public record TransferRequest(String fromAccount,String toAccount,float amount) {

}
