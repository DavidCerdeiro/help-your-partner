package com.help_your_partner.user_service.application.dto;

public class RegisterUserCommand {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String newCommunityName;
    private String existingCommunityCode;

    public RegisterUserCommand(String name, String surname, String email, String password, String newCommunityName) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.newCommunityName = newCommunityName;
        this.existingCommunityCode = null;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
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

}
