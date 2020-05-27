package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.domain.model.Allergen;
import com.kemenu.kemenu_backend.domain.model.Dish;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DishMapper {

    private final CloudinaryService cloudinaryService;

    public List<Dish> from(MenuSectionRequest menuSectionRequest) {
        return menuSectionRequest.getDishes().stream()
                .map(d -> Dish.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> Allergen.builder().id(a.getId()).name(a.getName()).build()).collect(toList()))
                        .imageUrl(Objects.isNull(d.getPhoto()) ? "" : cloudinaryService.upload(d.getPhoto())) // TODO: This should be async
                        .build()
                )
                .collect(toList());
    }

    public List<DishResponse> from(MenuSection menuSection) {
        return menuSection.getDishes().stream()
                .map(d -> DishResponse.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> AllergenData.builder().id(a.getId()).name(a.getName()).build()).collect(toList()))
                        .imageUrl(d.getImageUrl())
                        .build()
                )
                .collect(toList());
    }
}
