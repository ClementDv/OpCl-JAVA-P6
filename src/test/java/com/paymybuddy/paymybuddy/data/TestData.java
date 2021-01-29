package com.paymybuddy.paymybuddy.data;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.Operation;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestData {

    public static User getTrueUserData() {
        return new User().setId(1L).setBalance(999.99).setEmail("test@test.com").setPassword("abcd");
    }

    public static UserDetailsImpl getPrincipal() {
        return new UserDetailsImpl(1L, "test@test.com", "abcd");
    }

    public static UserDTO getUserDTOFromUserData() {
        return new UserDTO().build(getTrueUserData());
    }

    public static Optional<User> getOptionalUserData() {
        return Optional.of(getTrueUserData());
    }

    /*public static List<Operation> getOperationList() {
        return new ArrayList<>(Arrays.asList(
                new Operation("USR_clement@gmail.com", "BNK_BNP", 2000).setAt(null),
                new Operation("USR_clement@gmail.com", "BNK_LCL", 600).setAt(null),
                new Operation("USR_try@gmail.com", "USR_bibi@lol.com", 500).setAt(null),
                new Operation("USR_giva@test.com", "BNK_BNP", 400).setAt(null),
                new Operation("USR_gamin@gmail.com", "USER_test@test.Com", 300).setAt(null),
                new Operation("USR_jean@gmail.com", "BNK_TEST", 150).setAt(null))
        );
    }*/

    public static List<OperationDTO> getOperationDTOList() {
        return new ArrayList<>(Arrays.asList(
                new OperationDTO("USR_clement@gmail.com", "BNK_BNP", null, 2000),
                new OperationDTO("USR_clement@gmail.com", "BNK_LCL", null, 600),
                new OperationDTO("USR_try@gmail.com", "USR_bibi@lol.com", null, 500),
                new OperationDTO("USR_giva@test.com", "BNK_BNP", null, 400),
                new OperationDTO("USR_gamin@gmail.com", "USER_test@test.Com", null, 300),
                new OperationDTO("USR_jean@gmail.com", "BNK_TEST", null, 150))
        );
    }

    public static UserDTO getUserDTOTransferMoneyToBankOrUser() {
        return new UserDTO(1L, "test@test.com", 899.99);
    }

    public static UserDTO getUserDTOTransgerMoneyFromBank() {
        return new UserDTO(1L, "test@test.com", 1099.99);
    }

   /* public static List<Operation> getOperationListRepositoryTest() {
        return new ArrayList<>(Arrays.asList(
                new Operation("BNK_BNP", "USR_clement@ocr.fr", 300.00).setId(2L),
                new Operation("USR_clement@ocr.fr", "BNK_BNP", 200.00).setId(1L)
        ));
    }*/

    public static UserDetailsImpl getUserDetailsImplFromUser() {
        return UserDetailsImpl.build(getTrueUserData());
    }
}
