package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Business {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private List<Menu> menus;

    public Business(String name) {
        this(UUID.randomUUID().toString(), name, new ArrayList<>());
    }

    public void createMenu(Menu menu) {
        menus.add(menu);
    }

    public void changeMenu(Menu newMenu) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).equals(newMenu)) {
                menus.set(i, newMenu);
                return;
            }
        }
    }

    public List<Menu> menuList() {
        return Collections.unmodifiableList(menus);
    }

    public Optional<Menu> findMenu(String menuId) {
        return menus.stream().filter(m -> m.getId().equals(menuId)).findFirst();
    }
}
