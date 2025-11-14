package com.help_your_partner.task_service.application.dto;

public class ClaimTaskCommand {
    public String taskId;
    public String claimantId;

    public ClaimTaskCommand(String taskId, String claimantId) {
        this.taskId = taskId;
        this.claimantId = claimantId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getClaimantId() {
        return claimantId;
    }
    public void setClaimantId(String claimantId) {
        this.claimantId = claimantId;
    }
}