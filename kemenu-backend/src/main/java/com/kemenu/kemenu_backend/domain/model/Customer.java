package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Customer implements GrantedAuthority {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private List<Business> businesses;
    private Role role;

    public Customer(String email, String password, String businessName) {
        this(email, password, Role.USER, businessName);
    }

    public Customer(String email, String password, Role role, String businessName) {
        this(UUID.randomUUID().toString(), email, password, new ArrayList<>(), role);
        businesses.add(new Business(businessName));
    }

    public String createMenu(Business business, Menu menu) {
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
        return menu.getId();
    }

    public String changeMenu(Business business, Menu newMenu) {
        businesses.stream()
                .filter(b -> b.equals(business))
                .findFirst()
                .ifPresent(b -> b.changeMenu(newMenu));
        return newMenu.getId();
    }

    public Optional<Menu> findMenu(String businessId, String menuId) {
        Optional<Business> optionalBusiness = findBusiness(businessId);

        if (optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        return optionalBusiness.get().findMenu(menuId);
    }

    public Optional<Business> findBusiness(String businessId) {
        return businesses.stream()
                .filter(b -> b.getId().equals(businessId))
                .findFirst();
    }

    public Business firstBusiness() {
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
