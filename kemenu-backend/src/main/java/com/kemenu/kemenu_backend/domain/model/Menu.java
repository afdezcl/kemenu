package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Menu {

    @Id
    private String id;
    private Map<String, MenuSection> sections;

    public Menu() {
        this(UUID.randomUUID().toString(), new HashMap<>());
    }

    public void addNewSection(String sectionName) {
        sections.put(sectionName, MenuSection.builder().name(sectionName).dishes(new ArrayList<>()).build());
    }

    public void addNewDish(String sectionName, Dish dish) {
        if (!sections.containsKey(sectionName)) {
            addNewSection(sectionName);
        }
        sections.get(sectionName).addNewDish(dish);
    }

    public int numberOfSections() {
        return sections.size();
    }

    public int numberOfDishes(String sectionName) {
        if (sections.containsKey(sectionName)) {
            return sections.get(sectionName).numberOfDishes();
        } else {
            return 0;
        }
    }
}
