package com.kemenu.kemenu_backend.domain.model;

import java.util.Optional;

public interface ConfirmedEmailRepository {
    String save(ConfirmedEmail confirmedEmail);

    Optional<ConfirmedEmail> findByEmail(String email);

    Optional<ConfirmedEmail> findById(String id);
}
