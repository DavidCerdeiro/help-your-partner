package com.help_your_partner.user_service.infrastructure.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.model.User;
import com.help_your_partner.user_service.infrastructure.persistence.mongodb.MongoCommunityRepositoryAdapter;
import com.help_your_partner.user_service.infrastructure.persistence.mongodb.MongoUserRepositoryAdapter;
import com.mongodb.assertions.Assertions;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class MongoUserRepositoryAdapterIT {
    
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private MongoUserRepositoryAdapter userRepository;
    @Autowired
    private MongoCommunityRepositoryAdapter communityRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection("users");
    }

    @Test
    public void testSave() {
        Community community = new Community();
        User user = new User();
        user.setName("Alice");
        user.setSurname("Smith");
        user.setEmail("alice.smith@example.com");
        user.setPasswordHash("hashed_password_456");
        community.setName("Test Community");

        community = communityRepository.save(community);
        user.setCommunityId(community.getId());
        userRepository.save(user);
        
        Assertions.assertNotNull(userRepository.findByEmail("alice.smith@example.com"));
    }

    @Test
    public void testNullWhenUserNotFound() {
        User user = userRepository.findByEmail("non.existent@example.com");
        Assertions.assertNull(user);
    }
}
