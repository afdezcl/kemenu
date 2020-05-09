package com.kemenu.kemenu_backend.domain.model;

public interface CustomerRepository {
    String create(Customer customer);

    boolean exists(Customer customer);
}
