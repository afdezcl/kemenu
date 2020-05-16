package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class ShortUrlMongoRepository implements ShortUrlRepository {

    private final ShortUrlSpringMongoRepository springMongoRepository;

    @Override
    public String save(ShortUrl shortUrl) {
        return springMongoRepository.save(shortUrl).getId();
    }

    @Override
    public Optional<ShortUrl> findById(String id) {
        return springMongoRepository.findById(id);
    }
}
