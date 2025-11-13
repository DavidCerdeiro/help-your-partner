package com.help_your_partner.task_service.application.dto;

public class PostTaskCommand {
    public String title;
    public String description;
    public String userId;
    public String communityId;

    public PostTaskCommand() {
    }
    public PostTaskCommand(String title, String description, String userId, String communityId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.communityId = communityId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCommunityId() {
        return communityId;
    }
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
