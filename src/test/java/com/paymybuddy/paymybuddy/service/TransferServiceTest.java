package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.exception.NoBankFoundException;
import com.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.paymybuddy.paymybuddy.exception.NonValidAmountException;
import com.paymybuddy.paymybuddy.model.Bank;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.BankRepository;
import com.paymybuddy.paymybuddy.repository.OperationRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.impl.TransferServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Import(TransferServiceImpl.class)
public class TransferServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BankRepository bankRepository;

    @MockBean
    private OperationRepository operationRepository;

    @Autowired
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationToken(TestData.getPrincipal(), null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void transferMoneyToBankTest() {
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(new Bank());
        Mockito.when(userRepository.findBalanceById(Mockito.anyLong())).thenReturn(999.99);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User().setId(1L).setEmail("test@test.com").setBalance(899.99)));
        Assertions.assertThat(transferService.transferMoneyToBank(
                "BNK_BNP", 100.00, SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(TestData.getUserDTOTransferMoneyToBankOrUser());
    }

    @Test
    public void transferMoneyFromBank() {
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(new Bank());
        Mockito.when(userRepository.findBalanceById(Mockito.anyLong())).thenReturn(999.99);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User().setId(1L).setEmail("test@test.com").setBalance(1099.99)));
        Assertions.assertThat(transferService.transferMoneyFromBank(
                "BNK_BNP", 100.00, SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(TestData.getUserDTOTransgerMoneyFromBank());
    }

    @Test
    public void transferMoneyToUser() {
        Mockito.when(userRepository.findIdByEmail(Mockito.anyString())).thenReturn(2L);
        Mockito.when(userRepository.findBalanceById(Mockito.anyLong())).thenReturn(999.99);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User().setId(1L).setEmail("test@test.com").setBalance(899.99)));
        Assertions.assertThat(transferService.transferMoneyToUser(
                "BNK_BNP", 100.00, SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(TestData.getUserDTOTransferMoneyToBankOrUser());
    }

    @Test
    public void NonValidAmountExceptionTest() {
        Assertions.assertThatThrownBy(() -> transferService.transferMoneyToUser("test", 300.222,  SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NonValidAmountException.class);
    }

    @Test
    public void NoEnoughMoneyOnBalanceExceptionTest() {
        Mockito.when(userRepository.findBalanceById(Mockito.anyLong())).thenReturn(10.00);
        Assertions.assertThatThrownBy(() -> transferService.transferMoneyToUser("test", 11.00,  SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoEnoughMoneyOnBalanceException.class);
    }

    @Test
    public void NoBankFoundException() {
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(null);
        Assertions.assertThatThrownBy(() -> transferService.transferMoneyToBank("BNP", 10.00,  SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoBankFoundException.class);
    }
}
