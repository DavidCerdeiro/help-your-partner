package com.help_your_partner.task_service.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.help_your_partner.task_service.domain.model.Task;

public interface TaskRepository {
    Optional<Task> save(Task task);
    Task findById(String id);
    List<Task> findByUserId(String userId);
    List<Task> findByCommunity(String userId);
}
