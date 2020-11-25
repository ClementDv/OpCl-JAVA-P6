package com.example.paymybuddy.paymybuddy.controller;

import com.example.paymybuddy.paymybuddy.dto.UserDTO;
import com.example.paymybuddy.paymybuddy.service.ContactService;
import com.example.paymybuddy.paymybuddy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymybuddy")
public class ApiController {

    private final UserService userService;

    private final ContactService contactService;

    public ApiController(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @PutMapping("/transferMoneyToBank")
    private UserDTO transferMoneyToBank(@RequestParam double amount, Authentication authentication) {
         return userService.transferMoneyToBank(amount, authentication);
    }

    @PutMapping("/transferMoneyFromBank")
    private UserDTO transferMoneyFromBank(@RequestParam double amount, Authentication authentication) {
        return userService.transferMoneyFromBank(amount, authentication);
    }

    @PostMapping("/addContact")
    private void addContact(@RequestParam String contactEmail, Authentication authentication) {
        contactService.addContact(contactEmail, authentication);
    }
}
