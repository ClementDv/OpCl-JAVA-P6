package com.paymybuddy.paymybuddy.security.service;

import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.exception.NonValidEmailLogin;
import com.paymybuddy.paymybuddy.exception.UserAlreadyExistException;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service()
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger logger = LogManager.getLogger(AuthenticationService.class);

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NoUserFoundException(email);
        }
        return UserDetailsImpl.build(user);
    }

    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        checkValidMail(jwtRequest.getEmail());
        if (authenticate(jwtRequest.getEmail(), jwtRequest.getPassword())) {
            final UserDetailsImpl userDetails = loadUserByUsername(jwtRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            logger.info("Request login successful");
            return new JwtResponse(token, userDetails.getUsername(), userDetails.getId());
        }
        return null;
    }

    @Transactional
    public String save(JwtRequest registerRequest) {
        checkValidMail(registerRequest.getEmail());
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistException(registerRequest.getEmail());
        }
        User user = new User(registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
        logger.info("Request register successful");
        return "User registered successfully!";
    }

    private boolean authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new NoUserFoundException(username);
        }
        return true;
    }

    private void checkValidMail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NonValidEmailLogin(email);
        }
    }
}

