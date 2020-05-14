package com.kemenu.kemenu_backend.domain.model;

import java.util.Optional;

public interface BusinessRepository {
    String create(Business business);

    Optional<Business> read(String id);
}
