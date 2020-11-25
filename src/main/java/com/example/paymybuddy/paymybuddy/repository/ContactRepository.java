package com.example.paymybuddy.paymybuddy.repository;

import com.example.paymybuddy.paymybuddy.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository  extends JpaRepository<Contact, Long> {
}
