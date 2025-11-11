package com.help_your_partner.user_service.application.dto;

public class LoginUserResponse {
    private String token;

    public LoginUserResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
