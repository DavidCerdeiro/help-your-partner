package com.help_your_partner.task_service.infrastructure.web.dto;

import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;

public class ClaimTaskRequest {
    public String taskId;

    public ClaimTaskRequest() {
    }

    public String getTaskId() {
        return taskId;
    }

    public ClaimTaskCommand toCommand() {
        ClaimTaskCommand command = new ClaimTaskCommand();
        command.setTaskId(this.taskId);
        return command;
    }
}
