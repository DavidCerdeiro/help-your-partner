package com.help_your_partner.task_service.application.dto;

public class ClaimTaskCommand {
    public String taskId;

    public ClaimTaskCommand() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}