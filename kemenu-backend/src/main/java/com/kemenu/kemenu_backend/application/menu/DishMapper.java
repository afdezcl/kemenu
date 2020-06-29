package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DishMapper {

    private final CloudinaryService cloudinaryService;

    public List<DishResponse> from(MenuSection menuSection) {
        return menuSection.getDishes().stream()
                .map(d -> DishResponse.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .allergens(d.getAllergens().stream().map(a -> AllergenData.builder().id(a.getId()).name(a.getName()).build()).collect(toList()))
                        .imageUrl(!StringUtils.isEmpty(d.getImageUrl()) ? cloudinaryService.getOptimizedUrl(d.getImageUrl()) : "")
                        .available(isNull(d.getAvailable()) ? true : d.getAvailable()) // TODO: Refactor when frontend use it
                        .build()
                )
                .collect(toList());
    }
}
