package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Contact;
import com.paymybuddy.paymybuddy.repository.ContactRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.data.TestData;
import com.paymybuddy.paymybuddy.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@Import(ContactServiceImpl.class)
public class ContactServiceTest {

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void addContactTest() {
        Mockito.when(userRepository.findIdByEmail(Mockito.anyString())).thenReturn(2L);
        Mockito.when(contactRepository.findByUserIdAndContactId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
        contactService.addContact("clement", SecurityContextHolder.getContext().getAuthentication());
        Mockito.verify(contactRepository, Mockito.times(1)).save(Mockito.any());
    }
}
