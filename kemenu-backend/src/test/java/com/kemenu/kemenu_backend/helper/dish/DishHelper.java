package com.kemenu.kemenu_backend.helper.dish;

import com.kemenu.kemenu_backend.domain.model.Dish;
import com.kemenu.kemenu_backend.helper.allergen.AllergenHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishHelper {

    public static Dish randomDish() {
        return Dish.builder()
                .name(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .allergens(
                        IntStream.rangeClosed(0, 2) // 3 allergens
                        .mapToObj(i -> AllergenHelper.random())
                        .collect(toList())
                )
                .build();
    }
}
