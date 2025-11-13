package com.help_your_partner.task_service.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;
import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.application.dto.PostTaskCommand;
import com.help_your_partner.task_service.application.port.in.ClaimTaskUseCase;
import com.help_your_partner.task_service.application.port.in.CreateTaskUseCase;
import com.help_your_partner.task_service.application.port.in.GetTasksUseCase;
import com.help_your_partner.task_service.domain.model.Task;
import com.help_your_partner.task_service.domain.model.TaskStatus;
import com.help_your_partner.task_service.domain.port.out.TaskRepository;

@Service
public class TaskService implements ClaimTaskUseCase, CreateTaskUseCase, GetTasksUseCase{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public boolean claimTask(ClaimTaskCommand command) {
        Task task = taskRepository.findById(command.getTaskId());
        if (task != null && task.getStatus() == TaskStatus.OPEN) {
            task.setStatus(TaskStatus.CLAIMED);
            task.setClaimantId(command.getClaimantId());
            Optional<Task> savedTask = taskRepository.save(task);
            return savedTask.isPresent();
        }

        return false;
    }
    @Override
    public boolean createTask(PostTaskCommand request) {
        Task task = new Task(null, request.getTitle(), request.getDescription(), request.getUserId(), null, request.getCommunityId(), TaskStatus.OPEN);
        Optional<Task> savedTask = taskRepository.save(task);
        return savedTask.isPresent();
        
    }

    @Override
    public GetTasksResponse getTasks(String communityId) {
        List<Task> tasks = taskRepository.findByCommunity(communityId);
        return new GetTasksResponse(tasks);
    }
}