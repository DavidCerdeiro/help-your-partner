package com.help_your_partner.task_service.infrastructure.web.dto;

import com.help_your_partner.task_service.application.dto.PostTaskCommand;

public class PostTaskRequest {
    public String title;
    public String description;

    public PostTaskRequest() {
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

    public PostTaskCommand toCommand() {
        PostTaskCommand command = new PostTaskCommand();
        command.setTitle(this.title);
        command.setDescription(this.description);
        return command;
    }
}
