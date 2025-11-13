package com.help_your_partner.task_service.application.port.in;

import com.help_your_partner.task_service.application.dto.PostTaskCommand;

public interface CreateTaskUseCase {
    void createTask(PostTaskCommand request);
}
