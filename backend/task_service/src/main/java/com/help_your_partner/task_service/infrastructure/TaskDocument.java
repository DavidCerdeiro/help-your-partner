package com.help_your_partner.task_service.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.help_your_partner.task_service.domain.model.TaskStatus;

@Document(collection = "tasks")
public class TaskDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private String claimantId;
    private String communityId;
    private TaskStatus status;

    public TaskDocument() {
    }

    public TaskDocument(String id, String title, String description, String creatorId, String claimantId, String communityId, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.claimantId = claimantId;
        this.communityId = communityId;
        this.status = status;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
