package com.help_your_partner.task_service.domain.model;


public class Task {
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private String claimantId;
    private String communityId;
    private TaskStatus status;

    public Task(String id, String title, String description, String creatorId, String claimantId, String communityId, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.claimantId = claimantId;
        this.communityId = communityId;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getClaimantId() {
        return claimantId;
    }
    public void setClaimantId(String claimantId) {
        this.claimantId = claimantId;
    }

    public String getCommunityId() {
        return communityId;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
