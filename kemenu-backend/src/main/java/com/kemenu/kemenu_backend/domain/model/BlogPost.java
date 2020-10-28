package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Document("blogPosts")
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class BlogPost {
    @Id
    @EqualsAndHashCode.Include
    private final String id;
    @Indexed(unique = true)
    private final String readableId;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final String createdBy;
    private final String updatedBy;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
