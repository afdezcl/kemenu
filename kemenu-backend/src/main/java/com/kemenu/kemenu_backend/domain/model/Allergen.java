package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Allergen {

    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    public Allergen(String name, String description, String imageUrl) {
        this(UUID.randomUUID().toString(), name, description, imageUrl);
    }
}
