package com.bankingApp.BankingApplication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Authentication {
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
