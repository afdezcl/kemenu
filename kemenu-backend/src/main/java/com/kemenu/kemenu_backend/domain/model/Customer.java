package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Customer implements GrantedAuthority {

    @Id
    @Getter
    @EqualsAndHashCode.Include
    private String id;
    @Getter
    @Indexed(unique = true)
    private String email;
    @Getter
    private String password;

    @DBRef
    private List<Business> businesses;
    private Role role;

    public Customer(String email, String password) {
        this(UUID.randomUUID().toString(), email, password, new ArrayList<>(), Role.USER);
    }

    public Customer(String email, String password, String businessName) {
        this(UUID.randomUUID().toString(), email, password, new ArrayList<>(), Role.USER);
        businesses.add(new Business(businessName));
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

    public Business getFirstBusiness() {
        return businesses.get(0);
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }

    public enum Role {
        ADMIN, USER
    }
}
