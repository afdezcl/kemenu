package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.application.menu.DishData;
import com.kemenu.kemenu_backend.application.menu.MenuRequest;
import com.kemenu.kemenu_backend.application.menu.MenuSectionData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequestHelper {

    public static MenuRequest randomRequest() {
        return randomRequest(UUID.randomUUID().toString());
    }

    public static MenuRequest randomRequest(String businessId) {
        return MenuRequest.builder()
                .businessId(businessId)
                .sections(List.of(MenuSectionData.builder()
                        .name(UUID.randomUUID().toString())
                        .dishes(List.of(DishData.builder()
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
