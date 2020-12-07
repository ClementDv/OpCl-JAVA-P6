package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.Operation;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.TransferService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymybuddy")
public class ApiController {

    private final UserService userService;

    private final TransferService transferService;

    private final ContactService contactService;

    public ApiController(UserService userService, TransferService transferService, ContactService contactService) {
        this.userService = userService;
        this.transferService = transferService;
        this.contactService = contactService;
    }

    @PutMapping("/transferMoneyToBank")
    private UserDTO transferMoneyToBank(@RequestParam String bank, @RequestParam double amount, Authentication authentication) {
        return transferService.transferMoneyToBank(bank, amount, authentication);
    }

    @PutMapping("/transferMoneyFromBank")
    private UserDTO transferMoneyFromBank(@RequestParam String bank, @RequestParam double amount, Authentication authentication) {
        return transferService.transferMoneyFromBank(bank, amount, authentication);
    }

    @PutMapping("/transferMoneyToUser")
    private UserDTO transferMoneyToUser(@RequestParam String email, @RequestParam double amount, Authentication authentication) {
        return transferService.transferMoneyToUser(email, amount, authentication);
    }

    @PostMapping("/addContact")
    private void addContact(@RequestParam String contactEmail, Authentication authentication) {
        contactService.addContact(contactEmail, authentication);
    }

    @GetMapping("/informations")
    private UserDTO getPersonalInformation(Authentication authentication) {
        return userService.getPersonalInformation(authentication);
    }

    @GetMapping("/operations")
    private List<OperationDTO> getOperations(@RequestParam(required = false) Integer limit, Authentication authentication) {
        return userService.getOperations(limit, authentication);
    }
}
