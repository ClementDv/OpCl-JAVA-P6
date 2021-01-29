package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByIdEmailTest() {
        testEntityManager.persist(new User("test@test.com", "abcd", 999.99));
        testEntityManager.flush();
        assertThat(userRepository.findByEmail("test@test.com")).isEqualTo(TestData.getTrueUserData());
    }

    @Test
    void existsByEmailTest() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd"));
        testEntityManager.flush();
        assertThat(userRepository.existsByEmail("clement@ocr.fr")).isTrue();
    }

    @Test
    void findByIdTest() {
        testEntityManager.persist(new User("test@test.com", "abcd", 999.99));
        testEntityManager.flush();
        assertThat(userRepository.findById(1L)).isEqualTo(Optional.of(TestData.getTrueUserData()));
    }

    @Test
    @DisplayName("find id by email should return an id for an existing user")
    void findIdByEmailShouldReturnAUserIdTest() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd"));
        testEntityManager.flush();
        assertThat(userRepository.findIdByEmail("clement@ocr.fr")).isNotNull();
    }

    @Test
    @DisplayName("find id by email should return null for a missing user")
    void findIdByEmailShouldReturnNullTest() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd"));
        testEntityManager.flush();
        assertThat(userRepository.findIdByEmail("john.doe@ocr.fr")).isNull();
    }

    @Test
    void findBalanceByIdTest() {
        testEntityManager.persist(new User("clement@ocr.fr", "pwd", 222.22));
        testEntityManager.flush();
        assertThat(userRepository.findBalanceById(1L)).isEqualTo(222.22);
    }
}
