package com.help_your_partner.task_service.application.service;

import org.springframework.stereotype.Service;

import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.application.dto.PostTaskRequest;
import com.help_your_partner.task_service.application.port.in.ClaimTaskUseCase;
import com.help_your_partner.task_service.application.port.in.CreateTaskUseCase;
import com.help_your_partner.task_service.application.port.in.GetTasksUseCase;

@Service
public class TaskService implements ClaimTaskUseCase, CreateTaskUseCase, GetTasksUseCase{

    @Override
    public void claimTask(String taskId) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void createTask(PostTaskRequest request) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public GetTasksResponse getTasks(String communityId) {
        // TODO Auto-generated method stub
        return null;
    }
}