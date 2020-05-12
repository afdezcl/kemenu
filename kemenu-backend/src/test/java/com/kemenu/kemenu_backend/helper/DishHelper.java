package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Dish;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishHelper {

    public static Dish randomDish() {
        return Dish.builder()
                .name(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .allergens(List.of())
                .build();
    }
}
