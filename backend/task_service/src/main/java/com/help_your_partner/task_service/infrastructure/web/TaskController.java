package com.help_your_partner.task_service.infrastructure.web;

import org.springframework.web.bind.annotation.RestController;

import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.infrastructure.web.dto.ClaimTaskRequest;
import com.help_your_partner.task_service.infrastructure.web.dto.PostTaskRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class TaskController {
    @PostMapping("/tasks")
    public ResponseEntity<String> postTask(@RequestBody PostTaskRequest request) {
        //TODO: process POST request
        
        return null;
    }
    
    @GetMapping("/tasks")
    public ResponseEntity<GetTasksResponse> getTasks() {
        return null;
    }
    
    @PostMapping("/tasks/claim")
    public String postMethodName(@RequestBody ClaimTaskRequest request) {
        //TODO: process POST request
        
        return null;
    }
    
}
