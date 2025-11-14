package com.help_your_partner.task_service.domain.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TaskTest {
    @Test
    public void testTaskCreation() {
        Task task = new Task("1", "Test Task", "This is a test task.", "creator1", "claimant1", "community1");

        Assertions.assertEquals("1", task.getId());
        Assertions.assertEquals("Test Task", task.getTitle());
        Assertions.assertEquals("This is a test task.", task.getDescription());
        Assertions.assertEquals("creator1", task.getCreatorId());
        Assertions.assertEquals("claimant1", task.getClaimantId());
        Assertions.assertEquals("community1", task.getCommunityId());
        Assertions.assertEquals(TaskStatus.OPEN, task.getStatus());
    }
}
