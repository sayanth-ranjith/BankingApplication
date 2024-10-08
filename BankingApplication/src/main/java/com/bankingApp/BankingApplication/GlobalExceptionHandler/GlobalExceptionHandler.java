package com.bankingApp.BankingApplication.GlobalExceptionHandler;

import com.bankingApp.BankingApplication.ExceptionHandling.AccountNotFoundException;
import com.bankingApp.BankingApplication.ExceptionHandling.InsufficientFundException;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger log;

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> accountNotFound(){
        return new ResponseEntity<>("Account not found!",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<?> insufficientFund(){
        return new ResponseEntity<>("Insufficient funds for this",HttpStatus.METHOD_NOT_ALLOWED);
    }

}
