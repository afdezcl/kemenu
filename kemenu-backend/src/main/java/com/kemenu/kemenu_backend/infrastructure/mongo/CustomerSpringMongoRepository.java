package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface CustomerSpringMongoRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
}
