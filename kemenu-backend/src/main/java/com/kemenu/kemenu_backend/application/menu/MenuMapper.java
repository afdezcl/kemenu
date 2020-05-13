package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;

    public Menu from(MenuRequest menuRequest) {
        Menu menu = new Menu();
        menuRequest.getSections().stream()
                .collect(toMap(MenuSectionRequest::getName, dishMapper::from))
                .forEach(menu::addDishes);
        return menu;
    }
}
