package com.example.paymybuddy.paymybuddy.service.impl;

import com.example.paymybuddy.paymybuddy.dto.UserDTO;
import com.example.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.example.paymybuddy.paymybuddy.exception.NonValidAmountException;
import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.example.paymybuddy.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO transferMoneyToBank(double amount, Authentication authentication) {
        checkAmountIsValidHundredthsDecimalMax(amount);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
        double userBalance = userRepository.findBalanceById(currentUserId);
        if (userBalance >= amount) {
            logger.info("Request transfer money to bank successful");
            userRepository.updateBalanceById((userBalance - amount), currentUserId);
            return UserDTO.build(userRepository.findById(currentUserId).get());
        }
        logger.info("Request transfer money to bank failed");
        throw new NoEnoughMoneyOnBalanceException(userBalance, amount);
    }

    @Override
    public UserDTO transferMoneyFromBank(double amount, Authentication authentication) {
        checkAmountIsValidHundredthsDecimalMax(amount);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
        double userBalance = userRepository.findBalanceById(currentUserId);
        userRepository.updateBalanceById((userBalance + amount), currentUserId);
        logger.info("Request transfer money to bank successful");
        return UserDTO.build(userRepository.findById(currentUserId).get());
    }

    public void checkAmountIsValidHundredthsDecimalMax(double amount) {
        boolean valid = (BigDecimal.valueOf(amount).scale() <= 2);
        if (valid == false) {
            throw new NonValidAmountException(amount);
        }
    }

}
