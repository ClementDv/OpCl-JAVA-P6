package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.model.Bank;
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
public class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void findByNameTest() {
        testEntityManager.persist(new Bank("BNP", "42 avenue JEANJAU"));
        testEntityManager.flush();
        assertThat(bankRepository.findByName("BNP")).isEqualTo(new Bank("BNP", "42 avenue JEANJAU"));
    }
}
