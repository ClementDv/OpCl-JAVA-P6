package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.repository.OperationRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final OperationRepository operationRepository;

    private final UserRepository userRepository;


    public UserServiceImpl(OperationRepository operationRepository, UserRepository userRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String getOperations(int count, Authentication authentication) {
        return null;
    }

    @Override
    public UserDTO getPersonalInformation(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return UserDTO.build(userRepository.findById(userDetails.getId()).get());
    }
}
