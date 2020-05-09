package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

interface BusinessSpringMongoRepository extends MongoRepository<Business, String> {
}
