package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Customer {

    @Id
    private UUID id;
    @Indexed(unique = true)
    private String email;
    private String password;

    @DBRef
    private List<Business> businesses;

    public Customer(String email, String password) {
        this(UUID.randomUUID(), email, password, new ArrayList<>());
    }

    public void createMenu(Business business, Menu menu) {
        businesses.stream()
                .filter(b -> b.equals(business))
                .findFirst()
                .ifPresentOrElse(
                        b -> b.createMenu(menu),
                        () -> {
                            business.createMenu(menu);
                            businesses.add(business);
                        }
                );
    }

    public List<Menu> menuList(Business business) {
        return businesses.stream()
                .filter(b -> b.equals(business))
                .findFirst()
                .map(Business::menuList)
                .orElse(List.of());
    }
}
