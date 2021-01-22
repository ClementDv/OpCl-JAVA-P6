package com.paymybuddy.paymybuddy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.config.AuthenticationMananagerProvider;
import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.TransferService;
import com.paymybuddy.paymybuddy.service.UserService;
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
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class) // JUnit5
@WebMvcTest(ApiController.class)
@ContextConfiguration(classes = {AuthenticationMananagerProvider.class})
// Cette configuration fournit le Bean AuthenticationManager pour le context
@Import({ApiController.class}) // Important de l'importer sinon on des 404
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
        Mockito.when(transferService.transferMoneyToBank(Mockito.anyString(), Mockito.anyDouble(), Mockito.any()))
                .thenReturn(TestData.getUserDTOTransferMoneyToBankOrUser());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToBank?bank=BNP&amount=100")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOTransferMoneyToBankOrUser()).getJson());
    }

    @Test
    public void transferMoneyToUser() throws Exception {
        // GIVEN
        Mockito.when(transferService.transferMoneyToUser(Mockito.anyString(), Mockito.anyDouble(), Mockito.any()))
                .thenReturn(TestData.getUserDTOTransferMoneyToBankOrUser());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyToUser?email=clement@Test.com&amount=100")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOTransferMoneyToBankOrUser()).getJson());
    }


    @Test
    public void transferMoneyFromBank() throws Exception {
        // GIVEN
        Mockito.when(transferService.transferMoneyFromBank(Mockito.anyString(), Mockito.anyDouble(), Mockito.any()))
                .thenReturn(TestData.getUserDTOTransgerMoneyFromBank());

        // WHEN
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/paymybuddy/transferMoneyFromBank?bank=BNP&amount=100")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // THEN
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(jsonTester.write(TestData.getUserDTOTransgerMoneyFromBank()).getJson());
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
        mvc.perform(MockMvcRequestBuilders.post("/paymybuddy/addContact?contactEmail=test@test.com")
                .with(csrf()).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }
}
