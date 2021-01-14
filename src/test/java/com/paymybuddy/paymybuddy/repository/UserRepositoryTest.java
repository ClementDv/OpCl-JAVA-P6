package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("find id by email should return an id for an existing user")
    void findIdByEmailShouldReturnAUserId() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd"));
        testEntityManager.flush();
        assertThat(userRepository.findIdByEmail("clement@ocr.fr")).isNotNull();
    }
    @Test
    @DisplayName("find id by email should return null for a missing user")
    void findIdByEmailShouldReturnNull() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd"));
        testEntityManager.flush();
        assertThat(userRepository.findIdByEmail("john.doe@ocr.fr")).isNull();
    }

    @Test
    void findBalanceById() {
    }

    @Test
    void updateBalanceById() {
    }
}
