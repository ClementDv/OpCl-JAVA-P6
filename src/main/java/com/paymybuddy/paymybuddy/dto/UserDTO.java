package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.model.User;

import javax.validation.constraints.Email;

public class UserDTO {
    private Long id;
    @Email
    private String email;
    private double balance;

    public UserDTO() {
    }

    public static UserDTO build(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getBalance());
    }

    public UserDTO(Long id, String email, double balance) {
        this.id = id;
        this.email = email;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public UserDTO setBalance(double balance) {
        this.balance = balance;
        return this;
    }
}
