package com.help_your_partner.task_service.infrastructure.persistence.mongodb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.help_your_partner.task_service.infrastructure.TaskDocument;

@Repository
public interface SpringDataMongoTaskRepository extends MongoRepository<TaskDocument, String> {
    Optional<TaskDocument> findById(String id);
    List<TaskDocument> findByCommunityId(String communityId);
    List<TaskDocument> findByCreatorId(String userId);
} 
