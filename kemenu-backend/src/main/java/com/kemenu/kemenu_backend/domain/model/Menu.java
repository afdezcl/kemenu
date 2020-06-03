package com.kemenu.kemenu_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Menu {

    String id;
    List<MenuSection> sections;
    String imageUrl;

    public Menu() {
        this(UUID.randomUUID().toString(), new ArrayList<>(), "");
    }

    public Menu(List<MenuSection> sections) {
        this(UUID.randomUUID().toString(), sections, "");
    }

    public Menu(String id, List<MenuSection> sections) {
        this(id, sections, "");
    }
}
