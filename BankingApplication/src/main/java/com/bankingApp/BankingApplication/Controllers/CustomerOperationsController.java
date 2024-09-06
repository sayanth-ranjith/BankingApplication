package com.bankingApp.BankingApplication.Controllers;

import com.bankingApp.BankingApplication.DTO.CustomerDto;
import com.bankingApp.BankingApplication.Entity.Customer;

import com.bankingApp.BankingApplication.Service.CustomerDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
/**
 * This Controller will be used only to handle simple operations.
 * Lets consider this like a test controller developed with no plan just to test the sql connections and endpoint working.
 * */

@RestController
@RequestMapping("/customer/operation")
public class CustomerOperationsController {
    @Autowired
    CustomerDetailService customerDetailService;
    @GetMapping("/getCustomerDetails/{accountNumber}")
    private ResponseEntity<?> getName(@PathVariable Long accountNumber){
        try {
            List<Customer> customerDetails = customerDetailService.findByAccountNumber(accountNumber);
            if(customerDetails.size()==0){
                return new ResponseEntity<>("No Customer Details Found",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customerDetails,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Service unavailable now.Internal error.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createCustomer( @RequestBody @Valid CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setState(customerDto.getState());
        customer.setCountry(customerDto.getCountry());
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());
        customer.setGender(customerDto.getGender());
        customer.setAccountNumber(customerDto.getAccountNumber());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAlternativePhone(customerDto.getAlternativePhone());
        customer.setPinCode(customerDto.getPinCode());

        customerDetailService.save(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
