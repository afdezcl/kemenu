package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface BlogPostSpringMongoRepository extends MongoRepository<BlogPost, String> {
    Optional<BlogPost> findByReadableId(String readableId);
}
