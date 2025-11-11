package com.help_your_partner.user_service.infrastructure.persistence.mongodb;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.model.User;
import com.help_your_partner.user_service.domain.port.out.UserRepository;
import com.help_your_partner.user_service.infrastructure.UserDocument;
import com.help_your_partner.user_service.infrastructure.persistence.mongodb.repository.SpringDataMongoUserRepository;

@Repository
public class MongoUserRepositoryAdapter implements UserRepository{
    private final SpringDataMongoUserRepository repository;
    private final MongoCommunityRepositoryAdapter communityRepository;

    public MongoUserRepositoryAdapter(SpringDataMongoUserRepository repository, MongoCommunityRepositoryAdapter communityRepository) {
        this.repository = repository;
        this.communityRepository = communityRepository;
    }

    @Override
    public void save(User user) {
        UserDocument userDocument = new UserDocument(
            user.getId(),
            user.getName(),
            user.getSurname(),
            user.getEmail(),
            user.getPasswordHash(),
            user.getCommunityId()
        );
        repository.save(userDocument);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserDocument> doc = repository.findByEmail(email);
        if(doc.isPresent()) {
            UserDocument userDoc = doc.get();
            Community community = communityRepository.findById(userDoc.getCommunityId()).orElse(null);
            return new User(
                userDoc.getId(),
                userDoc.getName(),
                userDoc.getSurname(),
                userDoc.getEmail(),
                userDoc.getPassword(),
                community.getId()
            );
        }

        return null;
    }
}
