package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface ShortUrlSpringMongoRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findByCustomerEmail(String customerEmail);
}
