package com.kemenu.kemenu_backend.domain.model;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    String save(Customer customer);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findById(String id);

    List<Customer> all();
}
