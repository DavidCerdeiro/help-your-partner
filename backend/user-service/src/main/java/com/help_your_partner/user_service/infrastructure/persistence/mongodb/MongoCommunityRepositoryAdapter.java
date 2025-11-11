package com.help_your_partner.user_service.infrastructure.persistence.mongodb;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.port.out.CommunityRepository;
import com.help_your_partner.user_service.infrastructure.CommunityDocument;
import com.help_your_partner.user_service.infrastructure.persistence.mongodb.repository.SpringDataMongoCommunityRepository;

@Repository
public class MongoCommunityRepositoryAdapter implements CommunityRepository {
    private final SpringDataMongoCommunityRepository communityRepository;

    public MongoCommunityRepositoryAdapter(SpringDataMongoCommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Community save(Community community) {
        CommunityDocument communityDocument = new CommunityDocument(
            community.getId(),
            community.getName(),
            community.getCode()
        );
        CommunityDocument savedDocument = communityRepository.save(communityDocument);
        return new Community(
            savedDocument.getId(),
            savedDocument.getName(),
            savedDocument.getCode()
        );
    }

    @Override
    public Optional<Community> findById(String id) {
        return communityRepository.findById(id)
            .map(doc -> new Community(
                doc.getId(),
                doc.getName(),
                doc.getCode()
            ));
    }

    @Override
    public Optional<Community> findByCode(String code) {
        return communityRepository.findByCode(code)
            .map(doc -> new Community(
                doc.getId(),
                doc.getName(),
                doc.getCode()
            ));

    }
}
