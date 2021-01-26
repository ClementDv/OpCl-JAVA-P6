package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.exception.NoBankFoundException;
import com.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.exception.NonValidAmountException;
import com.paymybuddy.paymybuddy.model.Bank;
import com.paymybuddy.paymybuddy.model.MoneyHolder;
import com.paymybuddy.paymybuddy.model.Operation;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.BankRepository;
import com.paymybuddy.paymybuddy.repository.OperationRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.TransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final BankRepository bankRepository;

    private final OperationRepository operationRepository;

    public TransferServiceImpl(UserRepository userRepository, BankRepository bankRepository, OperationRepository operationRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public UserDTO transferMoneyToBank(String bankName, double amount, Authentication authentication) {
        checkAmountIsValidHundredthsDecimalMax(amount);
        Bank bank = checkBankisValid(bankName);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
        double userBalance = userRepository.findBalanceById(currentUserId);
        if (userBalance >= amount) {
            logger.info("Request transfer money to bank successful");
            userRepository.updateBalanceById((userBalance - amount), currentUserId);
            addTransferToOperation(new User().setEmail(userDetails.getUsername()), bank, amount);
            return UserDTO.build(userRepository.findById(currentUserId).get());
        }
        logger.info("Request transfer money to bank failed");
        throw new NoEnoughMoneyOnBalanceException(userBalance, amount);
    }

    @Override
    public UserDTO transferMoneyFromBank(String bankName, double amount, Authentication authentication) {
        checkAmountIsValidHundredthsDecimalMax(amount);
        Bank bank = checkBankisValid(bankName);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
        double userBalance = userRepository.findBalanceById(currentUserId);
        userRepository.updateBalanceById((userBalance + amount), currentUserId);
        logger.info("Request transfer money to bank successful");
        addTransferToOperation(bank, new User().setEmail(userDetails.getUsername()), amount);
        return UserDTO.build(userRepository.findById(currentUserId).get());
    }

    @Override
    public UserDTO transferMoneyToUser(String email, double amount, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (email.equals(userDetails.getUsername())) throw new NoUserFoundException(email);
        checkAmountIsValidHundredthsDecimalMax(amount);
        Long userTransferId = userRepository.findIdByEmail(email);
        if (userTransferId == null) {
            throw new NoUserFoundException(email);
        } else {
            Long currentUserId = userDetails.getId();
            double userBalance = userRepository.findBalanceById(currentUserId);
            if (userBalance >= amount) {
                userRepository.updateBalanceById((userRepository.findBalanceById(userTransferId) + percentageDeduction(amount)), userTransferId);
                userRepository.updateBalanceById((userBalance - amount), currentUserId);
                logger.info("Request transfer money to user successful");
                addTransferToOperation(new User().setEmail(userDetails.getUsername()), new User().setEmail(email), amount);
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

    private Bank checkBankisValid(String bankName) {
        Bank bank = bankRepository.findByName(bankName);
        if (bank == null) {
            throw new NoBankFoundException(bankName);
        }
        return bank;
    }

    private void addTransferToOperation(MoneyHolder emitter, MoneyHolder receiver, double amount) {
        operationRepository.save(new Operation(emitter.getCode(), receiver.getCode(), amount));
    }
}
