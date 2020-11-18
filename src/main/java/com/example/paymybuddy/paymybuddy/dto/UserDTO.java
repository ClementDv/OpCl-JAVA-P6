package com.example.paymybuddy.paymybuddy.dto;

import com.example.paymybuddy.paymybuddy.models.entity.User;

import javax.validation.constraints.Email;

public class UserDTO {
    private int id;
    @Email
    private String email;
    private double money;

    public UserDTO() {
    }

    public UserDTO(int id, String email, double money) {
        this.email = email;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void build(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
    }
}
