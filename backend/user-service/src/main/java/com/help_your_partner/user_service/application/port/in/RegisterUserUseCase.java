package com.help_your_partner.user_service.application.port.in;

import com.help_your_partner.user_service.application.dto.RegisterUserCommand;
import com.help_your_partner.user_service.application.dto.RegisterUserResponse;

public interface RegisterUserUseCase {
    RegisterUserResponse registerUser(RegisterUserCommand user);
}
