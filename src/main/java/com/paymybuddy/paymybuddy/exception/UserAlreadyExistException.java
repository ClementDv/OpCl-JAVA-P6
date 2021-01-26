package com.paymybuddy.paymybuddy.exception;

public class UserAlreadyExistException extends RuntimeException {
    private String email;

    public UserAlreadyExistException(String email) {
        super("User already exist");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
