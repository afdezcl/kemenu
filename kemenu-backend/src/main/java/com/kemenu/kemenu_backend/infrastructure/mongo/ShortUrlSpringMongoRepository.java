package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ShortUrlSpringMongoRepository extends MongoRepository<ShortUrl, String> {
}
