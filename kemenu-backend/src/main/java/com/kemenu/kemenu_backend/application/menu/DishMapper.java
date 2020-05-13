package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.domain.model.Allergen;
import com.kemenu.kemenu_backend.domain.model.Dish;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DishMapper {

    public List<Dish> from(MenuSectionData menuSectionData) {
        return menuSectionData.getDishes().stream()
                .map(d -> Dish.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> new Allergen(a.getName(), "", a.getImageUrl())).collect(toList()))
                        .build()
                )
                .collect(toList());
    }

    public List<DishData> from(MenuSection menuSection) {
        return menuSection.getDishes().stream()
                .map(d -> DishData.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> AllergenData.builder().name(a.getName()).imageUrl(a.getImageUrl()).build()).collect(toList()))
                        .build()
                )
                .collect(toList());
    }
}
