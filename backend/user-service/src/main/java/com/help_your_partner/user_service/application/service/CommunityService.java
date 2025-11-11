package com.help_your_partner.user_service.application.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.port.out.CommunityRepository;
import com.mongodb.DuplicateKeyException;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    public Community createCommunity(String communityName) {

        Community community = null;
        boolean savedSuccessfully = false;

        while (!savedSuccessfully) {
            String inviteCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();

            community = new Community(null, communityName, inviteCode);

            try {
                communityRepository.save(community);
                savedSuccessfully = true;

            } catch (DuplicateKeyException e) {
                // If a duplicate key exception occurs, it means the invite code is not unique.
                // We will generate a new code and try again.
            }
        }
        
        return community;
    }

    public Community findByCode(String code) {
        return communityRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));
    }
}
