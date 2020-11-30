package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    String getOperations(int count, Authentication authentication);

    UserDTO getPersonalInformation(Authentication authentication);
}
