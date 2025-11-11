package com.help_your_partner.user_service.infrastructure.web.dto;

import com.help_your_partner.user_service.application.dto.RegisterUserCommand;

public class RegisterUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String newCommunityName;
    private String existingCommunityCode;

    public RegisterUserRequest(String name, String surname, String email, String password, String newCommunityName, String existingCommunityCode) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.newCommunityName = newCommunityName;
        this.existingCommunityCode = existingCommunityCode;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewCommunityName() {
        return newCommunityName;
    }

    public String getExistingCommunityCode() {
        return existingCommunityCode;
    }

    public RegisterUserCommand toCommand() {
        if (existingCommunityCode != null && !existingCommunityCode.isEmpty()) {
            return new RegisterUserCommand(name, surname, email, password, existingCommunityCode);
        } else {
            return new RegisterUserCommand(name, surname, email, password, newCommunityName);
        }
    }
}
