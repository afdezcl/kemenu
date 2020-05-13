package com.kemenu.kemenu_backend.domain.model;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);

    Optional<Menu> findById(String id);
}
