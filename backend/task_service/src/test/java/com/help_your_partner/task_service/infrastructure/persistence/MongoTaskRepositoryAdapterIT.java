package com.help_your_partner.task_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.help_your_partner.task_service.domain.model.Task;
import com.help_your_partner.task_service.infrastructure.persistence.mongodb.MongoTaskRepositoryAdapter;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class MongoTaskRepositoryAdapterIT {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private MongoTaskRepositoryAdapter taskRepository;
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
        Task task = new Task(null, "Test Task", "This is a test task", "creator123", null, "community123", null);
        Optional<Task> savedTask = taskRepository.save(task);
        assert(savedTask.isPresent());
        assert(savedTask.get().getId() != null);
    }

    @Test
    public void testFindById() {
        Task task = new Task(null, "Test Task", "This is a test task", "creator123", null, "community123", null);
        Optional<Task> savedTask = taskRepository.save(task);
        assert(savedTask.isPresent());
        String taskId = savedTask.get().getId();

        Task foundTask = taskRepository.findById(taskId);
        assert(foundTask != null);
        assert(foundTask.getId().equals(taskId));
    }

    @Test
    public void testFindByCreatorId() {
        Task task = new Task(null, "Test Task", "This is a test task", "creator123", null, "community123", null);
        Optional<Task> savedTask = taskRepository.save(task);
        assert(savedTask.isPresent());
        
        List<Task> foundTask = taskRepository.findByCreatorId("creator123");
        assert(foundTask.size() > 0);
    }

    @Test
    public void testFindByCommunityId() {
        Task task = new Task(null, "Test Task", "This is a test task", "creator123", null, "community123", null);
        Optional<Task> savedTask = taskRepository.save(task);
        assert(savedTask.isPresent());
        
        List<Task> foundTask = taskRepository.findByCommunityId("community123");
        assert(foundTask.size() > 0);
    }

}
