package com.bankingApp.BankingApplication.Security;

import com.bankingApp.BankingApplication.Entity.AuthenticationTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {


    private AuthenticationTable authenticationTable;

    public CustomUserDetails(AuthenticationTable authenticationTable) {
        super();
        this.authenticationTable = authenticationTable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return authenticationTable.getPassword();
    }

    @Override
    public String getUsername() {
        return authenticationTable.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
