package com.example.paymybuddy.paymybuddy.dto;

public class UserDto {
    private int id;
    private String email;
    private double money;

    public UserDto(int id, String email, double money) {
        this.id = id;
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
}
