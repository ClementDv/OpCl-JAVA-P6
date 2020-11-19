package com.example.paymybuddy.paymybuddy.controller;

import com.example.paymybuddy.paymybuddy.models.entity.User;
import com.example.paymybuddy.paymybuddy.repository.ContactRepository;
import com.example.paymybuddy.paymybuddy.service.ContactService;
import com.example.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymybuddy")
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @PostMapping("/addContact")
    private void addContact(@RequestParam String contactEmail, Authentication authentication) {
        contactService.addContact(contactEmail, authentication);
    }
}
