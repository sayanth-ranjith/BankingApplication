package com.bankingApp.BankingApplication.ExceptionHandling;

public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException(String message) {
        super(message);
    }

    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
}
