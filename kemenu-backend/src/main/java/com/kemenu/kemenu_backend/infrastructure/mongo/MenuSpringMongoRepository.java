package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

interface MenuSpringMongoRepository extends MongoRepository<Menu, String> {
}
