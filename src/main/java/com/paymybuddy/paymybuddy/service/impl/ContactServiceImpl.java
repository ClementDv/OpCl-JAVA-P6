package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.exception.ContactAlreadyAssignedException;
import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
import com.paymybuddy.paymybuddy.model.Contact;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.ContactRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.ContactService;
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
            logger.info("Request add contact failed");
            throw new NoUserFoundException(contactEmail);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (contactRepository.findByUserIdAndContactId(userDetails.getId(), contactId) != null) {
            logger.info("Request add contact failed");
            throw new ContactAlreadyAssignedException(userDetails.getId(), contactId);
        }
        if (!contactEmail.equals(userDetails.getUsername())) {
            logger.info("Request add contact successful");
            contactRepository.save(new Contact()
                    .setUser(new User().setId(userDetails.getId()))
                    .setContact(new User().setId(contactId))
            );
        }
        logger.info("Request add contact failed");
    }
}

