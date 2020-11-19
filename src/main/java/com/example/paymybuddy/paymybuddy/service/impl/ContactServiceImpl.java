package com.example.paymybuddy.paymybuddy.service.impl;

import com.example.paymybuddy.paymybuddy.models.entity.Contact;
import com.example.paymybuddy.paymybuddy.repository.ContactRepository;
import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addContact(String contactEmail, Authentication authentication) {
    }
}
