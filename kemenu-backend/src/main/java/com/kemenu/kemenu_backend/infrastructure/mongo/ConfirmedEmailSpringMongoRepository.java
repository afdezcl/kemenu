package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface ConfirmedEmailSpringMongoRepository extends MongoRepository<ConfirmedEmail, String> {
    Optional<ConfirmedEmail> findByEmail(String email);
}
