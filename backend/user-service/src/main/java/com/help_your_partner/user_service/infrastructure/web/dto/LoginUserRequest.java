package com.help_your_partner.user_service.infrastructure.web.dto;

import com.help_your_partner.user_service.application.dto.LoginUserCommand;

public class LoginUserRequest {
    private String email;
    private String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserCommand toCommand() {
        return new LoginUserCommand(email, password);
    }
}
