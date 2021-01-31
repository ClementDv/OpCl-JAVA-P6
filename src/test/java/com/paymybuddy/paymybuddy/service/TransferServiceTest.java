package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.dto.TransferRequest;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.exception.NoBankFoundException;
import com.paymybuddy.paymybuddy.exception.NoEnoughMoneyOnBalanceException;
import com.paymybuddy.paymybuddy.exception.NoUserFoundException;
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
    public void transferMoneyToBank() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(3000).setEmail("test@test.com"));
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(new Bank());
        Assertions.assertThat(transferService.transferMoneyToBank(
                new TransferRequest("BNP", 2000, "")
                , SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(TestData.getUserDTOTransferMoneyToBank());
    }

    @Test
    public void transferMoneyToBankNoEnoughMoneyOnBalanceException() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(3000).setEmail("test@test.com"));
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(new Bank());
        Assertions.assertThatThrownBy(() ->
                transferService.transferMoneyToBank(
                        new TransferRequest("BNP", 4000, "Test")
                        , SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoEnoughMoneyOnBalanceException.class);
    }

    @Test
    public void transferMoneyFromBank() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("test@test.com"));
        Mockito.when(bankRepository.findByName(Mockito.anyString())).thenReturn(new Bank());
        Assertions.assertThat(transferService.transferMoneyFromBank(
                new TransferRequest("BNP", 2000, "")
                , SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(TestData.getUserDTOTransferMoneyFromBank());
    }

    @Test
    public void NoBankFoundExcpetion() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("test@test.com"));
        Assertions.assertThatThrownBy(() ->
                transferService.transferMoneyFromBank(
                        new TransferRequest("WRONGBANK", 1000, "Test")
                        , SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoBankFoundException.class);
    }

    @Test
    public void transferMoneyToUser() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("opcl@gmail.com"));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(
                new User().setId(2L).setBalance(0).setEmail("opcl2@gmail.com"));
        Assertions.assertThat(transferService.transferMoneyToUser(
                new TransferRequest("opcl@gmail.com", 1000, "Test"),
                SecurityContextHolder.getContext().getAuthentication())
        ).isEqualTo(new UserDTO().setId(1L).setBalance(1000).setEmail("opcl@gmail.com"));
    }

    @Test
    public void TransferMoneyToUserNoCurrentUserFoundException() {
        Assertions.assertThatThrownBy(() ->
                transferService.transferMoneyToUser(new TransferRequest("test@test.com", 1000, "Test"), SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoUserFoundException.class);
    }

    @Test
    public void TransferMoneyToUserNoUserFoundException() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("opcl@gmail.com"));
        Assertions.assertThatThrownBy(() ->
                transferService.transferMoneyToUser(new TransferRequest("test", 1000, "Test"), SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoUserFoundException.class);
    }

    @Test
    public void TransferMoneyToUserNonValidAmountExceptionTest() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("opcl@gmail.com"));
        Assertions.assertThatThrownBy(() ->
                transferService.transferMoneyToUser(new TransferRequest("test", 1000.001, "Test"), SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NonValidAmountException.class);
    }

    @Test
    public void TransferMoneyToUserNoEnoughMon() {
        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(
                new User().setId(1L).setBalance(2000).setEmail("opcl@gmail.com"));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(
                new User().setId(2L).setBalance(0).setEmail("opcl2@gmail.com"));
        Assertions.assertThatThrownBy(() -> transferService.transferMoneyToUser(
                new TransferRequest("opcl2@gmail.com", 3000, "")
                , SecurityContextHolder.getContext().getAuthentication())
        ).isInstanceOf(NoEnoughMoneyOnBalanceException.class);
    }

}
