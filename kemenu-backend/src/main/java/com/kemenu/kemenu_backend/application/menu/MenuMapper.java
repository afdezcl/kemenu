package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;

    public Menu from(MenuRequest menuRequest) {
        Menu menu = new Menu();
        menuRequest.getSections().stream()
                .collect(toMap(MenuSectionData::getName, dishMapper::from))
                .forEach(menu::addDishes);
        return menu;
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
