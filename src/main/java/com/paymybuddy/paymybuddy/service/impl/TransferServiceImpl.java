package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.exception.NonValidAmountException;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.TransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final Logger logger = LogManager.getLogger(TransferServiceImpl.class);

    private final double percentageSample = 0.5;

    private final UserRepository userRepository;

    public TransferServiceImpl(UserRepository userRepository) {
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
        userRepository.updateBalanceById((userBalance + percentageDeduction(amount)), currentUserId);
        logger.info("Request transfer money to bank successful");
        return UserDTO.build(userRepository.findById(currentUserId).get());
    }

    @Override
    public UserDTO transferMoneyToUser(String email, double amount, Authentication authentication) {
        checkAmountIsValidHundredthsDecimalMax(amount);
        Long userTransferId = userRepository.findIdByEmail(email);
        if (userTransferId == null) {
            throw new NoUserFoundException(email);
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long currentUserId = userDetails.getId();
            double userBalance = userRepository.findBalanceById(currentUserId);
            if (userBalance >= amount) {
                userRepository.updateBalanceById((userRepository.findBalanceById(userTransferId) + percentageDeduction(amount)), userTransferId);
                userRepository.updateBalanceById((userBalance - amount), currentUserId);
                logger.info("Request transfer money to user successful");
                return UserDTO.build(userRepository.findById(currentUserId).get());
            }
            logger.info("Request transfer money to user failed");
            throw new NoEnoughMoneyOnBalanceException(userBalance, amount);
        }
    }

    public void checkAmountIsValidHundredthsDecimalMax(double amount) {
        if (!(BigDecimal.valueOf(amount).scale() <= 2)) {
            throw new NonValidAmountException(amount);
        }
    }

    private Double percentageDeduction(double amount) {
        double newAmount = (amount * (1 - (percentageSample / 100)));
        if (!(BigDecimal.valueOf(newAmount).scale() <= 2)) {
            return new BigDecimal(newAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return newAmount;
    }

}
