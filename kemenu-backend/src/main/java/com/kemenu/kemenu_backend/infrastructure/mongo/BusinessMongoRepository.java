package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.BusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class BusinessMongoRepository implements BusinessRepository {

    private final BusinessSpringMongoRepository springMongoRepository;

    @Override
    public String create(Business business) {
        return springMongoRepository.save(business).getId();
    }
}
