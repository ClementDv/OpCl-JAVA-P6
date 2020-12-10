package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.repository.OperationRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;
import com.paymybuddy.paymybuddy.service.data.TestData;
import com.paymybuddy.paymybuddy.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@Import(UserServiceImpl.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepositoryMocked;

    @MockBean
    private OperationRepository operationRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser()
    public void getInformationFromUserTest() {
        Mockito.when(userRepositoryMocked.findById(Mockito.anyLong())).thenReturn(TestData.getOptionalUserData());
        Assertions.assertThat(userService.getPersonalInformation(SecurityContextHolder.getContext().getAuthentication())).isEqualTo(TestData.getUserDTOFromUserData());
    }

}
