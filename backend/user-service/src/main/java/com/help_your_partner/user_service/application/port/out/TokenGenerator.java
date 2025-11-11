package com.help_your_partner.user_service.application.port.out;

import com.help_your_partner.user_service.domain.model.User;

public interface TokenGenerator {
    String generateToken(User user);
    boolean validateToken(String token);
}
