package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.controller.ApiController;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.security.service.LoggerService;
import com.paymybuddy.paymybuddy.service.data.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ApiController.class)
@ContextConfiguration(classes = {ServiceTestConfig.class, AuthorizationServerConfig.class})
public class ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void transferMoneyToBank(@Autowired MockMvc mvc) throws Exception {
        Mockito.when(transferService.transferMoneyToBank(Mockito.anyString(), Mockito.anyDouble(), Mockito.any()))
                .thenReturn(TestData.getUserDTOTransferMoneyToBankOrUser());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/transferMoneyToBank?bank=BNP&amount=100")
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assertions.assertThat(result).isEqualTo(TestData.getUserDTOTransferMoneyToBankOrUser());
    }

}
