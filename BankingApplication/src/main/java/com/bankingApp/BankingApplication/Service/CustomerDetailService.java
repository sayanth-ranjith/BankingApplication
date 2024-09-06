package com.bankingApp.BankingApplication.Service;
import java.util.*;
import com.bankingApp.BankingApplication.Entity.Customer;
import com.bankingApp.BankingApplication.Entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CustomerDetailService extends JpaRepository<Customer,Long> {
    List<Customer> findByAccountNumber(Long accountNumber);

    CustomerAccount save(CustomerAccount customerAccount);

}
