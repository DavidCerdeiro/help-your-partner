package com.help_your_partner.task_service.application.port.in;

import com.help_your_partner.task_service.application.dto.GetTasksResponse;

public interface GetTasksUseCase {
    GetTasksResponse getTasks(String communityId);
}
