package com.help_your_partner.task_service.application.port.in;

import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;

public interface ClaimTaskUseCase {
    boolean claimTask(ClaimTaskCommand command);
}
