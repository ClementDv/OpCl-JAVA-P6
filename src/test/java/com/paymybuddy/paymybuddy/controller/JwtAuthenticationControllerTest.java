package com.paymybuddy.paymybuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.config.AuthenticationMananagerProvider;
import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.paymybuddy.paymybuddy.security.model.response.JwtResponse;
import com.paymybuddy.paymybuddy.security.service.AuthenticationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class) // JUnit5
@WebMvcTest(JwtAuthenticationController.class)
@ContextConfiguration(classes = {AuthenticationMananagerProvider.class})
// Cette configuration fournit le Bean AuthenticationManager pour le context
@Import({JwtAuthenticationController.class})
        // Important de l'importer sinon on des 404
class JwtAuthenticationControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    MockMvc mvc;

    /**
     * Important pour tester le JSON de la réponse HTTP
     */
    private JacksonTester<JwtResponse> jsonTester;


    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createAuthenticationTokenShouldReturnOk() throws Exception {
        // GIVEN
        JwtResponse response = new JwtResponse("accesToken", "email", 1L);
        Mockito.when(authenticationService.createAuthenticationToken(Mockito.any(JwtRequest.class))).thenReturn(response);
        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/login").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"test@test.fr\", \"password\": \"pwd\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(response).getJson());
    }

    @Test
    void createAuthenticationTokenShouldReturnNotContent() throws Exception {
        // GIVEN
        JwtResponse response = new JwtResponse("accesToken", "email", 1L);
        Mockito.when(authenticationService.createAuthenticationToken(Mockito.any(JwtRequest.class))).thenReturn(null);
        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/login").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"test@test.fr\", \"password\": \"pwd\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
    }

    @Test
    void registerAUserShouldReturnOk() throws Exception {
        // GIVEN
        Mockito.when(authenticationService.save(Mockito.any(JwtRequest.class))).thenReturn("Request register successful");
        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/register").contentType(MediaType.APPLICATION_JSON).content("{\"email\": \"test@test.fr\", \"password\": \"pwd\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo("Request register successful");
    }
}
