package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import org.springframework.security.core.Authentication;

public interface TransferService {

    UserDTO transferMoneyToBank(String bankName, double amount, Authentication authentication);

    UserDTO transferMoneyFromBank(String bankName, double amount, Authentication authentication);

    UserDTO transferMoneyToUser(String email, double amount, Authentication authentication);
}
