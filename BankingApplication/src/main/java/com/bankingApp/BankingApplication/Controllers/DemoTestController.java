package com.bankingApp.BankingApplication.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoTestController {
    @GetMapping("/hello")
    public String method(){
      return "Hello !"  +" Go find yourself a better project.";
    }
}
