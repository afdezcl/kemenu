package com.kemenu.kemenu_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Business {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private List<Menu> menus;
    private String imageUrl;
    private String phone;
    private String info;
    private String color;

    public Business(String name) {
        this(UUID.randomUUID().toString(), name, new ArrayList<>(), "", "", "", "#e7475e");
    }

    public String createMenu(Menu menu) {
        menus.add(menu);
        return menu.getId();
    }

    public Optional<Menu> changeMenu(Menu newMenu) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getId().equals(newMenu.getId())) {
                menus.set(i, newMenu);
                return Optional.of(newMenu);
            }
        }

        return Optional.empty();
    }

    public Optional<Menu> findMenu(String menuId) {
        return menus.stream().filter(m -> m.getId().equals(menuId)).findFirst();
    }

    public void deleteMenu(String menuId) {
        menus.removeIf(menu -> menu.getId().equals(menuId));
    }
}
