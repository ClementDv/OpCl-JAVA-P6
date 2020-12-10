package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<OperationDTO> getOperations(Integer limit, Authentication authentication);

    UserDTO getPersonalInformation(Authentication authentication);

}
