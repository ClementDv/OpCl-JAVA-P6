package com.paymybuddy.paymybuddy.data;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.Bank;
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

    public static List<Operation> getOperationList() {
        return new ArrayList<>(Arrays.asList(
                new Operation(new Bank().setName("BNP").setAddress(""), null, null,
                        new User().setEmail("opcl1@test.com"), "TEST", 200),
                new Operation(null, new User().setEmail("opcl@test.com"), new Bank().setName("LCL").setAddress(""),
                        null, "TEST", 700),
                new Operation(null, new User().setEmail("test@test.com"), null,
                        new User().setEmail("test1@test.com"), "It's a test", 400),
                new Operation(null, new User().setEmail("cal@gmail.com"), null,
                        new User().setEmail("test@lol.com"), "we test", 300.55),
                new Operation(null, new User().setEmail("test@ab.com"), null,
                        new User().setEmail("try@test.com"), "try again", 100),
                new Operation(new Bank().setName("CA").setAddress(""), null, null,
                        new User().setEmail("transaction@tra.fr"), "test test", 53.44)
        ));
    }

    public static List<OperationDTO> getOperationDTOList() {
        return new ArrayList<>(Arrays.asList(
                new OperationDTO("BNK_BNP_", "USR_opcl1@test.com", null,
                        200, "TEST"),
                new OperationDTO("USR_opcl@test.com", "BNK_LCL_", null,
                        700, "TEST"),
                new OperationDTO("USR_test@test.com", "USR_test1@test.com", null,
                        400, "It's a test"),
                new OperationDTO("USR_cal@gmail.com", "USR_test@lol.com", null,
                        300.55,  "we test"),
                new OperationDTO("USR_test@ab.com", "USR_try@test.com", null,
                        100, "try again"),
                new OperationDTO("BNK_CA_", "USR_transaction@tra.fr", null,
                        53.44, "test test"))
        );
    }

    public static UserDTO getUserDTOTransferMoneyToBank() {
        return new UserDTO(1L, "test@test.com", 1000);
    }

    public static UserDTO getUserDTOTransferMoneyFromBank() {
        return new UserDTO(1L, "test@test.com", 4000);
    }
}
