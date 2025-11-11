package com.help_your_partner.user_service.domain.port.out;

import com.help_your_partner.user_service.domain.model.User;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
