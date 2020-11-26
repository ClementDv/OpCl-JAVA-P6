package com.paymybuddy.paymybuddy.exception;

public class NoUserFoundException extends RuntimeException {

    private final String email;

    public NoUserFoundException(String email) {
        super("No user found for this specific email");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
