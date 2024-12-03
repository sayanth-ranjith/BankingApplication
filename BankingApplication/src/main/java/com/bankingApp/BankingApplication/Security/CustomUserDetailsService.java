package com.bankingApp.BankingApplication.Security;

import com.bankingApp.BankingApplication.Entity.AuthenticationTable;
import com.bankingApp.BankingApplication.Service.AuthRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AuthRepoService authRepoService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthenticationTable values = authRepoService.findByEmail(email);
        if(values==null){
            throw new UsernameNotFoundException("User Not found");
        }

        return new CustomUserDetails(values);
    }
}
