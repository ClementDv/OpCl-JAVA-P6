package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.config.TestConfig;
import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.config.JwtTokenUtil;
import com.paymybuddy.paymybuddy.security.config.WebSecurityConfig;
import com.paymybuddy.paymybuddy.security.service.AuthenticationService;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@SpringBootTest(classes = {AuthenticationService.class, JwtTokenUtil.class})
@Import(TestConfig.class)
public class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void loadUserByUsernameTest() {
        User userTest = new User("test@test.com", "abcd").setId(1L);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userTest);
        Assertions.assertThat(authenticationService.loadUserByUsername(Mockito.anyString())).isEqualTo(UserDetailsImpl.build(userTest));
    }
}
