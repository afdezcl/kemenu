package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private List<String> menus;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public ShortUrl(String customerEmail, String businessId, String menuId) {
        this(UUID.randomUUID().toString(), customerEmail, businessId, new ArrayList<>(), Instant.now(), Instant.now());
        this.menus.add(menuId);
    }

    public ShortUrl(String id, String customerEmail, String businessId, List<String> menus) {
        this(id, customerEmail, businessId, menus, Instant.now(), Instant.now());
    }

    public Optional<String> findMenu(String menuId) {
        return menus.stream().filter(menuId::equals).findFirst();
    }
}
