package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class ConfirmedEmailMongoRepository implements ConfirmedEmailRepository {

    private final ConfirmedEmailSpringMongoRepository springMongoRepository;

    @Override
    public String save(ConfirmedEmail confirmedEmail) {
        return springMongoRepository.save(confirmedEmail).getId();
    }

    @Override
    public Optional<ConfirmedEmail> findByEmail(String email) {
        return springMongoRepository.findByEmail(email);
    }

    @Override
    public Optional<ConfirmedEmail> findById(String id) {
        return springMongoRepository.findById(id);
    }
}
