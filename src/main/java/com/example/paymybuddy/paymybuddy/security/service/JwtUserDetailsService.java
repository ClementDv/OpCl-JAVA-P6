package com.example.paymybuddy.paymybuddy.security.service;

import com.example.paymybuddy.paymybuddy.models.entity.User;
import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.security.config.WebSecurityConfig;
import com.example.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public ResponseEntity save(JwtRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already taken!");
        }
        User user = new User(registerRequest.getEmail(),
                webSecurityConfig.passwordEncoder().encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}