package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
class CustomerMongoRepository implements CustomerRepository {

    private final CustomerSpringMongoRepository springMongoRepository;

    @Override
    public String save(Customer customer) {
        return springMongoRepository.save(customer).getId();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return springMongoRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return springMongoRepository.findById(id);
    }

    @Override
    public List<Customer> all() {
        return springMongoRepository.findAll();
    }
}
