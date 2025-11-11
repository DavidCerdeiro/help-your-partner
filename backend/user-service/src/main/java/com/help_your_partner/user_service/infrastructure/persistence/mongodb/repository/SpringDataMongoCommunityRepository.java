package com.help_your_partner.user_service.infrastructure.persistence.mongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.help_your_partner.user_service.infrastructure.CommunityDocument;

@Repository
public interface SpringDataMongoCommunityRepository extends MongoRepository<CommunityDocument, String> {
    Optional<CommunityDocument> findById(String id);
    Optional<CommunityDocument> findByCode(String code);
}
