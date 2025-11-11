package com.help_your_partner.user_service.application.dto;

public class LoginUserCommand {
    private String email;
    private String password;

    public LoginUserCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
