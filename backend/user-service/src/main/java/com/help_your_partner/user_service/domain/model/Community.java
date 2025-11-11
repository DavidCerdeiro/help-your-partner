package com.help_your_partner.user_service.domain.model;

public class Community {
    private String id;
    private String name;
    private String code;

    public Community(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Community() {
        
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
