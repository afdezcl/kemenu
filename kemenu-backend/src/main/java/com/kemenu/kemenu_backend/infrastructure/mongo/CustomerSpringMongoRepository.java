package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CustomerSpringMongoRepository extends MongoRepository<Customer, UUID> {
}
