package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.domain.model.BlogPost;
import com.kemenu.kemenu_backend.domain.model.BlogPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
class BlogPostMongoRepository implements BlogPostRepository {

    private final BlogPostSpringMongoRepository repository;

    @Override
    public Optional<BlogPost> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<BlogPost> findByReadableId(String readableId) {
        return repository.findByReadableId(readableId);
    }

    @Override
    public List<BlogPost> all() {
        return repository.findAll();
    }
}
