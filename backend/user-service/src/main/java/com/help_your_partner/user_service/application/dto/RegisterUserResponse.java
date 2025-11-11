package com.help_your_partner.user_service.application.dto;

public class RegisterUserResponse {
    private String token;

    public RegisterUserResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
