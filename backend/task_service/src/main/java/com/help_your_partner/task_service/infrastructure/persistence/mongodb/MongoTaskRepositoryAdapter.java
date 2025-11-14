package com.help_your_partner.task_service.infrastructure.persistence.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.help_your_partner.task_service.domain.model.Task;
import com.help_your_partner.task_service.domain.port.out.TaskRepository;
import com.help_your_partner.task_service.infrastructure.TaskDocument;
import com.help_your_partner.task_service.infrastructure.persistence.mongodb.repository.SpringDataMongoTaskRepository;

@Repository
public class MongoTaskRepositoryAdapter implements TaskRepository {

    @Autowired
    private SpringDataMongoTaskRepository springDataMongoTaskRepository;
    
    @Override
    public Optional<Task> save(Task task) {
        TaskDocument taskDocument = new TaskDocument(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreatorId(),
            task.getClaimantId(),
            task.getCommunityId(),
            task.getStatus()
        );
        TaskDocument savedDocument = springDataMongoTaskRepository.save(taskDocument);
        if(savedDocument == null) {
            return Optional.empty();
        }
        Task savedTask = new Task(
            savedDocument.getId(),
            savedDocument.getTitle(),
            savedDocument.getDescription(),
            savedDocument.getCreatorId(),
            savedDocument.getClaimantId(),
            savedDocument.getCommunityId(),
            savedDocument.getStatus()
        );

        return Optional.of(savedTask);
    }

    @Override
    public Task findById(String id) {
        TaskDocument taskDocument = springDataMongoTaskRepository.findById(id).orElse(null);
        if (taskDocument == null) {
            return null;
        }
        return new Task(
            taskDocument.getId(),
            taskDocument.getTitle(),
            taskDocument.getDescription(),
            taskDocument.getCreatorId(),
            taskDocument.getClaimantId(),
            taskDocument.getCommunityId(),
            taskDocument.getStatus()
        );
    }

    @Override
    public List<Task> findByCreatorId(String creatorId) {
        return springDataMongoTaskRepository.findByCreatorId(creatorId).stream()
            .map(doc -> new Task(
                doc.getId(),
                doc.getTitle(),
                doc.getDescription(),
                doc.getCreatorId(),
                doc.getClaimantId(),
                doc.getCommunityId(),
                doc.getStatus()
            ))
            .toList();
    }

    @Override
    public List<Task> findByCommunityId(String communityId) {
        return springDataMongoTaskRepository.findByCommunityId(communityId).stream()
            .map(doc -> new Task(
                doc.getId(),
                doc.getTitle(),
                doc.getDescription(),
                doc.getCreatorId(),
                doc.getClaimantId(),
                doc.getCommunityId(),
                doc.getStatus()
            ))
            .toList();
    }
    
}
