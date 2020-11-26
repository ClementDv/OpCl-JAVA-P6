package com.paymybuddy.paymybuddy.security.model.request;

import javax.validation.constraints.Email;

public class JwtRequest {

    @Email
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
