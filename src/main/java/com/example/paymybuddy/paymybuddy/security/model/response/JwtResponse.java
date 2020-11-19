package com.example.paymybuddy.paymybuddy.security.model.response;

public class JwtResponse {
    private int id;
    private String email;
    private String token;
    private String type = "Bearer";

    public JwtResponse(String accessToken, String email, int id) {
        this.token = accessToken;
        this.email = email;
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}