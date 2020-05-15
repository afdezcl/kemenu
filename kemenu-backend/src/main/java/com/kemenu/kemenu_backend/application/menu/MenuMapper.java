package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;

    public Menu from(CreateMenuRequest createMenuRequest) {
        Menu menu = new Menu();
        createMenuRequest.getSections().stream()
                .collect(toMap(MenuSectionData::getName, dishMapper::from))
                .forEach(menu::addDishes);
        return menu;
    }

    public Menu from(UpdateMenuRequest updateMenuRequest) {
        Menu menuWithoutId = from(
                CreateMenuRequest.builder()
                        .businessId(updateMenuRequest.getBusinessId())
                        .sections(updateMenuRequest.getSections())
                        .build()
        );

        return new Menu(updateMenuRequest.getMenuId(), menuWithoutId.getSections());
    }

    public List<MenuResponse> from(List<Menu> menus) {
        return menus.stream().map(this::from).collect(toList());
    }

    public MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .sections(menu.getSections().entrySet().stream()
                        .map(e -> MenuSectionData.builder()
                                .name(e.getKey())
                                .dishes(dishMapper.from(e.getValue()))
                                .build())
                        .collect(toList())
                )
                .build();
    }
}
