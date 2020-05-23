package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.helper.dish.DishHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuSectionHelper {

    public static MenuSection randomSection() {
        return MenuSection.builder()
                .name(UUID.randomUUID().toString())
                .dishes(
                        IntStream.rangeClosed(0, 3) // 4 dishes
                                .mapToObj(i -> DishHelper.randomDish())
                                .collect(toList())
                )
                .build();
    }
}
