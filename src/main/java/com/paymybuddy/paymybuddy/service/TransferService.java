package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.TransferRequest;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import org.springframework.security.core.Authentication;

public interface TransferService {

    UserDTO transferMoneyToBank(TransferRequest transferRequest, Authentication authentication);

    UserDTO transferMoneyFromBank(TransferRequest transferRequest, Authentication authentication);

    UserDTO transferMoneyToUser(TransferRequest transferRequest, Authentication authentication);
}
