package com.paymybuddy.paymybuddy.service;

import org.springframework.security.core.Authentication;

public interface ContactService {
    void addContact(String contactEmail, Authentication authentication);
}
