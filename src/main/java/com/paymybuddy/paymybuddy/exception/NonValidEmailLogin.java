package com.paymybuddy.paymybuddy.exception;

public class NonValidEmailLogin extends RuntimeException {

    private String email;

    public NonValidEmailLogin(String email) {
        super("This email is not valid");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
