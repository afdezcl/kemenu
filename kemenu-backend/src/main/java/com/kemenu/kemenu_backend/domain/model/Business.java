package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Business {

    @Id
    @Getter
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    @DBRef
    private List<Menu> menus;

    public Business(String name) {
        this(UUID.randomUUID().toString(), name, new ArrayList<>());
    }

    public void createMenu(Menu menu) {
        menus.add(menu);
    }

    public List<Menu> menuList() {
        return Collections.unmodifiableList(menus);
    }
}
