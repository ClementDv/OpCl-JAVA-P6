package com.example.paymybuddy.paymybuddy.service.impl;

import com.example.paymybuddy.paymybuddy.model.Contact;
import com.example.paymybuddy.paymybuddy.model.User;
import com.example.paymybuddy.paymybuddy.repository.ContactRepository;
import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.example.paymybuddy.paymybuddy.service.ContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger logger = LogManager.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addContact(String contactEmail, Authentication authentication) {
        Long contactId = userRepository.findIdByEmail(contactEmail);
        if (contactId == null) {
            // TODO : throw ContactException
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            contactRepository.save(new Contact()
                    .setUser(new User().setId(userDetails.getId()))
                    .setContact(new User().setId(contactId))
            );
        }
    }
}
