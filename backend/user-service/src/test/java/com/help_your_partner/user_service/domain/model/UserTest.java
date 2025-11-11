package com.help_your_partner.user_service.domain.model;

import org.junit.jupiter.api.Test;

import com.mongodb.assertions.Assertions;

public class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();

        user.setName("David");
        user.setSurname("Martinez");
        user.setEmail("david.martinez@example.com");
        user.setPasswordHash("hashed_password_123");
        user.setCommunityId("community-id-456");

        Assertions.assertTrue(user.getName().equals("David"));
        Assertions.assertTrue(user.getSurname().equals("Martinez"));
        Assertions.assertTrue(user.getEmail().equals("david.martinez@example.com"));
        Assertions.assertTrue(user.getPasswordHash().equals("hashed_password_123"));
        Assertions.assertTrue(user.getCommunityId().equals("community-id-456"));
    }
}
