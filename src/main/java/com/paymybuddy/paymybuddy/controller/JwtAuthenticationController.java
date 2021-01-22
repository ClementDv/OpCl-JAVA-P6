package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.paymybuddy.paymybuddy.security.model.response.JwtResponse;
import com.paymybuddy.paymybuddy.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/paymybuddy")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        JwtResponse authenticationToken = userDetailsService.createAuthenticationToken(jwtRequest);
        return authenticationToken == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(authenticationToken);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<String> Register(@RequestBody JwtRequest registerRequest) {
        return ResponseEntity.ok(userDetailsService.save(registerRequest));
    }
}
