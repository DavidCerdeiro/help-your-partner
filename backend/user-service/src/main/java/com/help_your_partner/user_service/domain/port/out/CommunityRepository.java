package com.help_your_partner.user_service.domain.port.out;

import java.util.Optional;

import com.help_your_partner.user_service.domain.model.Community;

public interface CommunityRepository {
    Community save(Community community);
    Optional<Community> findById(String id);
    Optional<Community> findByCode(String code);
}

