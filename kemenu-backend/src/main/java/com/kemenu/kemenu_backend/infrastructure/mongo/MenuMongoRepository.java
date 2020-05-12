package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MenuMongoRepository implements MenuRepository {

    private final MenuSpringMongoRepository springMongoRepository;

    @Override
    public String create(Menu menu) {
        return springMongoRepository.save(menu).getId();
    }
}
