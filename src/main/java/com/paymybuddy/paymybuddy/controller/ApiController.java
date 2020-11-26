package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.TransferService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymybuddy")
public class ApiController {

    private final TransferService transferService;

    private final ContactService contactService;

    public ApiController(TransferService transferService, ContactService contactService) {
        this.transferService = transferService;
        this.contactService = contactService;
    }

    @PutMapping("/transferMoneyToBank")
    private UserDTO transferMoneyToBank(@RequestParam double amount, Authentication authentication) {
         return transferService.transferMoneyToBank(amount, authentication);
    }

    @PutMapping("/transferMoneyFromBank")
    private UserDTO transferMoneyFromBank(@RequestParam double amount, Authentication authentication) {
        return transferService.transferMoneyFromBank(amount, authentication);
    }

    @PutMapping("/transferMoneyToUser")
    private UserDTO transferMoneyToUser(@RequestParam String email, @RequestParam double amount, Authentication authentication) {
        return transferService.transferMoneyToUser(email, amount, authentication);
    }

    @PostMapping("/addContact")
    private void addContact(@RequestParam String contactEmail, Authentication authentication) {
        contactService.addContact(contactEmail, authentication);
    }
}
