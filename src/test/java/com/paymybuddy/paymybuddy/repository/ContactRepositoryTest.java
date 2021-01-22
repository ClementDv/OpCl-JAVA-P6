package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Contact;
import com.paymybuddy.paymybuddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByUserIdAndContactIdTest() {
        testEntityManager.persist(new User().setEmail("test").setPassword("aaaa"));
        testEntityManager.persist(new User().setEmail("tests").setPassword("aaaa"));
        testEntityManager.persist(new Contact().setUser(new User().setId(1L))
        .setContact(new User().setId(2L)));
        assertThat(contactRepository.findByUserIdAndContactId(1L, 2L)).isNotNull();
    }
}
