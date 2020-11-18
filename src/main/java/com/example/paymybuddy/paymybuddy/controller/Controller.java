package com.example.paymybuddy.paymybuddy.controller;

import com.example.paymybuddy.paymybuddy.models.entity.User;
import com.example.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private UserService userService;

    @GetMapping(value = "register")
    public User register(@RequestParam String email, @RequestParam String password) {
        return userService.register(email, password);
    }

    @GetMapping(value = "deletebyname")
    public void deleteUser(@RequestParam String email) {
        userService.deleteByName(email);
    }
}