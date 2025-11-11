package com.help_your_partner.user_service.infrastructure.persistence.mongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.help_your_partner.user_service.infrastructure.UserDocument;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByEmail(String email);
}
