package com.example.paymybuddy.paymybuddy.security.controller;

import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.security.config.JwtTokenUtil;
import com.example.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.example.paymybuddy.paymybuddy.security.model.response.JwtResponse;
import com.example.paymybuddy.paymybuddy.security.service.JwtUserDetailsService;
import com.example.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/paymybuddy")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token,
                userDetails.getUsername()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> Register(@RequestBody JwtRequest registerRequest) {
        return userDetailsService.save(registerRequest);
    }
}
