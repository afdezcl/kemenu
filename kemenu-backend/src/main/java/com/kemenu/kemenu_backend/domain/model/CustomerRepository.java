package com.kemenu.kemenu_backend.domain.model;

import java.util.UUID;

public interface CustomerRepository {
    UUID create(Customer customer);
}
