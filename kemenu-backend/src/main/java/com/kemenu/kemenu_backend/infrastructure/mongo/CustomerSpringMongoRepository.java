package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CustomerSpringMongoRepository extends MongoRepository<Customer, String> {
    boolean existsByEmail(String email);
}
