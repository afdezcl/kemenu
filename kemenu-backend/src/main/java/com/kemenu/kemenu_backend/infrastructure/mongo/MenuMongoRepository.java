package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuMongoRepository implements MenuRepository {

    private final MenuSpringMongoRepository springMongoRepository;

    @Override
    public String create(Menu menu) {
        return springMongoRepository.save(menu).getId();
    }

    @Override
    public Optional<Menu> findById(String id) {
        return springMongoRepository.findById(id);
    }
}
