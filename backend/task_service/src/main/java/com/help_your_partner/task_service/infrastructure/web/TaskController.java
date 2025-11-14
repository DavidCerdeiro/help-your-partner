package com.help_your_partner.task_service.infrastructure.web;

import org.springframework.web.bind.annotation.RestController;

import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;
import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.application.dto.PostTaskCommand;
import com.help_your_partner.task_service.application.port.in.ClaimTaskUseCase;
import com.help_your_partner.task_service.application.port.in.CreateTaskUseCase;
import com.help_your_partner.task_service.application.port.in.GetTasksUseCase;
import com.help_your_partner.task_service.infrastructure.web.dto.PostTaskRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    ClaimTaskUseCase claimTaskUseCase;

    @Autowired
    CreateTaskUseCase createTaskUseCase;

    @Autowired
    GetTasksUseCase getTasksUseCase;

    @PostMapping
    public ResponseEntity<String> postTask(@RequestBody PostTaskRequest request, Authentication authentication) {
        String userId = authentication.getPrincipal().toString();
        String communityId = (String) authentication.getDetails();
        PostTaskCommand command = new PostTaskCommand(request.getTitle(), request.getDescription(), userId, communityId);
        boolean created = createTaskUseCase.createTask(command);
        if (created) {
            return ResponseEntity.status(201).body("Task created successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to create task");
        }
    }
    
    @GetMapping
    public ResponseEntity<GetTasksResponse> getTasks(Authentication authentication) {
        String communityId = (String) authentication.getDetails();
        GetTasksResponse response = getTasksUseCase.getTasks(communityId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{taskId}/claim")
    public ResponseEntity<String> claimTask(@PathVariable String taskId, Authentication authentication) {
        String userId = authentication.getPrincipal().toString();
        
        boolean claimed = claimTaskUseCase.claimTask(new ClaimTaskCommand(taskId, userId));

        if (claimed) {
            return ResponseEntity.ok("Task claimed successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to claim task");
        }
    }
    
}
