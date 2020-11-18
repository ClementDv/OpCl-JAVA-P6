package com.example.paymybuddy.paymybuddy.service;

import com.example.paymybuddy.paymybuddy.models.entity.User;

public interface UserService {
    User saveUser(User user);

    void deleteUser(User user);

    User register(String email, String password);

    void deleteByName(String email);
}
