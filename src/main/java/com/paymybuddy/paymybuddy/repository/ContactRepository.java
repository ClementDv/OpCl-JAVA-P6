package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository  extends JpaRepository<Contact, Long> {
    Contact findByUserIdAndContactId(Long userId, Long contactId);
}
