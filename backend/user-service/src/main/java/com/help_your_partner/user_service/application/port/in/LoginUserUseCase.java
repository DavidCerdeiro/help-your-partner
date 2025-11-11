package com.help_your_partner.user_service.application.port.in;

import com.help_your_partner.user_service.application.dto.LoginUserCommand;
import com.help_your_partner.user_service.application.dto.LoginUserResponse;

public interface LoginUserUseCase {
    LoginUserResponse loginUser(LoginUserCommand request);
}
