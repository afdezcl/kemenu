package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class ShortUrl {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed
    private String customerEmail;
    private String businessId;
    private String menuId;

    public ShortUrl(String customerEmail, String businessId, String menuId) {
        this(UUID.randomUUID().toString(), customerEmail, businessId, menuId);
    }
}