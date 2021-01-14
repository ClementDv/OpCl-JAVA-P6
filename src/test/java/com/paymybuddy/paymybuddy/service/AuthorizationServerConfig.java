package com.paymybuddy.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class AuthorizationServerConfig {

    @Qualifier("authenticationManagerBean")
    @Autowired
    private AuthenticationManager authenticationManager;
}
