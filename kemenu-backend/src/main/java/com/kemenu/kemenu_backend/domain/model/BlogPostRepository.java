package com.kemenu.kemenu_backend.domain.model;

import java.util.Optional;

public interface BlogPostRepository {
    Optional<BlogPost> findById(String id);
}
