package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class CustomerMongoRepository implements CustomerRepository {

    private final CustomerSpringMongoRepository springMongoRepository;

    @Override
    public UUID create(Customer customer) {
        return springMongoRepository.save(customer).getId();
    }
}
