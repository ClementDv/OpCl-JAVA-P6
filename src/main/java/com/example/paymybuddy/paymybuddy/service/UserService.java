package com.example.paymybuddy.paymybuddy.service;

import com.example.paymybuddy.paymybuddy.dto.UserDTO;
import com.example.paymybuddy.paymybuddy.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserDTO transferMoneyToBank(double amount, Authentication authentication);

    UserDTO transferMoneyFromBank(double amount, Authentication authentication);
}
