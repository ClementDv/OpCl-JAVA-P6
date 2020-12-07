package com.paymybuddy.paymybuddy.security.service;

import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.exception.NonValidEmailLogin;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.config.JwtTokenUtil;
import com.paymybuddy.paymybuddy.security.config.WebSecurityConfig;
import com.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.paymybuddy.paymybuddy.security.model.response.JwtResponse;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
public class LoggerService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger logger = LogManager.getLogger(LoggerService.class);

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return UserDetailsImpl.build(user);
    }

    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        checkValidMail(jwtRequest.getEmail());
        if (authenticate(jwtRequest.getEmail(), jwtRequest.getPassword())) {
            final UserDetailsImpl userDetails = loadUserByUsername(jwtRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            logger.info("Request login successful");
            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), userDetails.getId()));
        }
        return ResponseEntity.notFound().build();
    }

    private boolean authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new NoUserFoundException(username);
        }
        return true;
    }

    @Transactional
    public ResponseEntity save(JwtRequest registerRequest) {
        checkValidMail(registerRequest.getEmail());
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new NoUserFoundException(registerRequest.getEmail());
        }
        User user = new User(registerRequest.getEmail(),
                webSecurityConfig.passwordEncoder().encode(registerRequest.getPassword()));

        userRepository.save(user);
        logger.info("Request register successful");
        return ResponseEntity.ok("User registered successfully!");
    }

    private void checkValidMail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NonValidEmailLogin(email);
        }
    }
}
