package com.paymybuddy.paymybuddy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.config.TestConfig;
import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.TransferRequest;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.exception.*;
import com.paymybuddy.paymybuddy.security.config.JwtAuthenticationEntryPoint;
import com.paymybuddy.paymybuddy.security.config.JwtTokenUtil;
import com.paymybuddy.paymybuddy.security.service.AuthenticationService;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.TransferService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ApiController.class)
@Import({TestConfig.class, JwtAuthenticationEntryPoint.class})
public class ControllerTest {

    /*
     * Dépendances du controller
     * */
    @MockBean
    private TransferService transferService;

    @MockBean
    private UserService userService;

    @MockBean
    private ContactService contactService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    AuthenticationManager authenticationManager;

    /**
     * Important pour tester le JSON de la réponse HTTP
     */
    private JacksonTester<UserDTO> jsonTester;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void transferMoneyToBank() throws Exception {
        // GIVEN
        Mockito.when(transferService.transferMoneyToBank(Mockito.any(TransferRequest.class), Mockito.any(Authentication.class))).thenAnswer(a -> {
            TransferRequest transferRequest = a.getArgument(0);
            if (BigDecimal.valueOf(transferRequest.getAmount()).scale() >= 3)
                throw new NonValidAmountException(transferRequest.getAmount());
            if (transferRequest.getName().equals("INVALID"))
                throw new NoBankFoundException(transferRequest.getName());
            if (transferRequest.getAmount() < 100)
                throw new NoEnoughMoneyOnBalanceException(transferRequest.getAmount(), 100);
            return TestData.getUserDTOTransferMoneyToBank();
        });

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToBank")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"BNP\", \"amount\": \"200\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOTransferMoneyToBank()).getJson());

        // Test errors
        //No enough money
        mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToBank")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"BNP\", \"amount\": \"50\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
        // Wrong amount
        mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToBank")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"BNP\", \"amount\": \"50.003\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
        // Invalid Bank
        mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToBank")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"INVALID\", \"amount\": \"50.00\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }

    @Test
    public void transferMoneyToUser() throws Exception {
        // GIVEN
        Mockito.when(transferService.transferMoneyToUser(Mockito.any(TransferRequest.class), Mockito.any()))
                .thenReturn(TestData.getUserDTOFromUserData());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToUser?email=clement@Test.com&amount=100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test@test.com\", \"amount\": \"100\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOFromUserData()).getJson());
    }

    @Test
    public void transferMoneyFromBank() throws Exception {
        // GIVEN
        Mockito.when(transferService.transferMoneyFromBank(Mockito.any(TransferRequest.class), Mockito.any()))
                .thenReturn(TestData.getUserDTOFromUserData());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyFromBank")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test@test.com\", \"amount\": \"100\", \"description\": \"Test\"}")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOFromUserData()).getJson());
    }

    @Test
    public void getOperationsTest(@Autowired MockMvc mockMvc) throws Exception {
        // GIVEN
        Mockito.when(userService.getOperations(Mockito.isNull(), Mockito.any()))
                .thenReturn(TestData.getOperationDTOList());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/paymybuddy/operations")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        List<OperationDTO> resultList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultList)
                .isEqualTo(TestData.getOperationDTOList());
    }

    @Test
    public void getInformationsTest(@Autowired MockMvc mockMvc) throws Exception {
        // GIVEN
        Mockito.when(userService.getPersonalInformation(Mockito.any()))
                .thenReturn(TestData.getUserDTOFromUserData());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/paymybuddy/informations")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOFromUserData()).getJson());
    }

    @Test
    public void addContactTest(@Autowired MockMvc mockMvc) throws Exception {

        // GIVEN
        Mockito.doAnswer(a -> {
                    String contactEmail = a.getArgument(0);
                    if (contactEmail.equals("")) {
                        throw new NoUserFoundException(contactEmail);
                    }
                    if (contactEmail.equals("alreadyExist")) {
                        throw new ContactAlreadyAssignedException(1L, 2L);
                    }
                    return null;
                }
        ).when(contactService).addContact(Mockito.anyString(), Mockito.any());

        // THEN
        mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/addContact?contactEmail=test@test.com")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // test errors
        mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/addContact?contactEmail=")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isPreconditionFailed()).andReturn();
        mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/addContact?contactEmail=alreadyExist")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
}
