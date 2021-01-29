package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.exception.NoBankFoundException;
import com.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.exception.NonValidAmountException;
import com.paymybuddy.paymybuddy.model.Bank;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;

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
        checkAmountIsValid(amount);
        Bank bank = checkBankisValid(bankName);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUser = userRepository.getOne(userDetails.getId());
        if (currentUser.getBalance() >= amount) {
            logger.info("Request transfer money to bank successful");
            currentUser.setBalance(round(currentUser.getBalance() - amount, 2));
            userRepository.save(currentUser);
            addTransferUserToBankOperation(currentUser, bank, amount);
            return UserDTO.build(currentUser);
        }
        logger.info("Request transfer money to bank failed");
        throw new NoEnoughMoneyOnBalanceException(currentUser.getBalance(), amount);
    }

    @Override
    public UserDTO transferMoneyFromBank(String bankName, double amount, Authentication authentication) {
        checkAmountIsValid(amount);
        Bank bank = checkBankisValid(bankName);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUser = userRepository.getOne(userDetails.getId());
        currentUser.setBalance(round((currentUser.getBalance() + amount), 2));
        userRepository.save(currentUser);
        logger.info("Request transfer money to bank successful");
        addTransferBankToUserOperation(bank, currentUser, amount);
        return UserDTO.build(currentUser);
    }

    @Override
    public UserDTO transferMoneyToUser(String email, double amount, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUser = userRepository.getOne(userDetails.getId());
        if (email.equals(userDetails.getUsername())) throw new NoUserFoundException(email);
        checkAmountIsValid(amount);
        User transferUser = userRepository.findByEmail(email);
        if (transferUser == null) {
            throw new NoUserFoundException(email);
        } else {
            if (currentUser.getBalance() >= amount) {
                transferUser.setBalance(round(transferUser.getBalance() + percentageDeduction(amount),2));
                userRepository.save(transferUser);
                currentUser.setBalance(round(currentUser.getBalance() - amount, 2));
                userRepository.save(currentUser);
                logger.info("Request transfer money to user successful");
                addTransferUserToUserOperation(currentUser, transferUser, amount);
                return UserDTO.build(currentUser);
            }
            logger.info("Request transfer money to user failed");
            throw new NoEnoughMoneyOnBalanceException(currentUser.getBalance(), amount);
        }
    }

    public void checkAmountIsValid(double amount) {
        if (!(BigDecimal.valueOf(amount).scale() <= 2) || amount <= 0) {
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

    public static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void addTransferUserToBankOperation(User emitter,  Bank receiver, double amount) {
        operationRepository.save(new Operation(null, emitter, receiver, null, "TEST", amount));
    }

    private void addTransferUserToUserOperation(User emitter,  User receiver, double amount) {
        operationRepository.save(new Operation(null, emitter, null, receiver, "TEST", amount));
    }

    private void addTransferBankToUserOperation(Bank emitter,  User receiver, double amount) {
        operationRepository.save(new Operation(emitter, null, null, receiver, "TEST", amount));
    }
}
