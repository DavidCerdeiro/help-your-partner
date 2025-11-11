package com.help_your_partner.user_service.domain.model;

public class User {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String passwordHash;
    private String communityId;

    public User(String id, String name, String surname, String email, String passwordHash, String communityId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.communityId = communityId; 
    }

    public User() {}

    public String getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCommunityId() {
        return communityId;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}