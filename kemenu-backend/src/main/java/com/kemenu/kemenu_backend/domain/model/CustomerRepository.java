package com.kemenu.kemenu_backend.domain.model;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    String create(Customer customer);

    Optional<Customer> findByEmail(String email);

    List<Customer> all();
}
