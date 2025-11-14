package com.help_your_partner.task_service.application.dto;

import java.util.List;

import com.help_your_partner.task_service.domain.model.Task;

public class GetTasksResponse {
    List<Task> tasks;

    public GetTasksResponse(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}