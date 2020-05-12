package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.application.menu.DishRequest;
import com.kemenu.kemenu_backend.application.menu.MenuRequest;
import com.kemenu.kemenu_backend.application.menu.MenuSectionRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequestHelper {

    public static MenuRequest randomRequest(String customerEmail) {
        return MenuRequest.builder()
                .customerEmail(customerEmail)
                .sections(List.of(MenuSectionRequest.builder()
                        .name(UUID.randomUUID().toString())
                        .dishes(List.of(DishRequest.builder()
                                .name(UUID.randomUUID().toString())
                                .description(UUID.randomUUID().toString())
                                .price(BigDecimal.TEN)
                                .allergens(List.of(AllergenData.builder()
                                        .name(UUID.randomUUID().toString())
                                        .build()))
                                .build()))
                        .build()))
                .build();
    }
}
