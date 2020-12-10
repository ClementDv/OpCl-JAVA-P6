package com.paymybuddy.paymybuddy.service.data;

import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.security.service.UserDetailsImpl;

import java.util.Optional;

public class TestData {

    public static User getTrueUserData(){
        return new User().setId(1L).setBalance(999.99).setEmail("test@test.com").setPassword("abcd");
    }

    public static UserDetailsImpl getPrincipal(){
        return new UserDetailsImpl(1L, "test@test.com", "abcd");
    }

    public static UserDTO getUserDTOFromUserData() {
        return new UserDTO().build(getTrueUserData());
    }

    public static Optional<User> getOptionalUserData() {
        return Optional.of(getTrueUserData());
    }

}
