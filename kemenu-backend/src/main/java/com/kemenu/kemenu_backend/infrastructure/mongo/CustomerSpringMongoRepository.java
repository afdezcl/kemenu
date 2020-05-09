package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

interface CustomerSpringMongoRepository extends MongoRepository<Customer, UUID> {
}
