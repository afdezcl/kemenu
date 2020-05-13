package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Allergen;
import com.kemenu.kemenu_backend.domain.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DishMapper {

    public List<Dish> from(MenuSectionRequest menuSectionRequest) {
        return menuSectionRequest.getDishes().stream()
                .map(d -> Dish.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> new Allergen(a.getName(), "", a.getImageUrl())).collect(toList()))
                        .build())
                .collect(toList());
    }
}
