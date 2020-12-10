package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.config.JwtTokenUtil;
import com.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.paymybuddy.paymybuddy.security.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/paymybuddy")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoggerService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return userDetailsService.createAuthenticationToken(jwtRequest);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<?> Register(@RequestBody JwtRequest registerRequest) {
        return userDetailsService.save(registerRequest);
    }
}
