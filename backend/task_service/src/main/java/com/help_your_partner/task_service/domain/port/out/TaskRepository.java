package com.help_your_partner.task_service.domain.port.out;

import java.util.List;

import com.help_your_partner.task_service.domain.model.Task;

public interface TaskRepository {
    void save(Task task);
    Task findById(String id);
    List<Task> findByUserId(String userId);
    List<Task> findByCommunity(String userId);
    void deleteById(String id);
}
