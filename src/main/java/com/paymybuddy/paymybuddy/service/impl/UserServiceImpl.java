package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.Operation;
import com.paymybuddy.paymybuddy.repository.OperationRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Integer OPERATION_LIMIT_DEFAULT_VALUE = 10;

    private final OperationRepository operationRepository;

    private final UserRepository userRepository;


    public UserServiceImpl(OperationRepository operationRepository, UserRepository userRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OperationDTO> getOperations(Integer limit, Authentication authentication) {
        if (limit == null) limit = OPERATION_LIMIT_DEFAULT_VALUE;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Operation> operationList = operationRepository.findByEmailReceiverOrEmitterWithLimitOrderByDate(userDetails.getId(), PageRequest.of(0, limit));
        System.out.println("\nOperation List : " + operationList + "\n");
        return operationList.stream().map(operation -> new OperationDTO().build(operation)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getPersonalInformation(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return UserDTO.build(userRepository.findById(userDetails.getId()).get());
    }
}

