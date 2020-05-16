package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.menu.DishData;
import com.kemenu.kemenu_backend.application.menu.MenuSectionData;
import com.kemenu.kemenu_backend.application.menu.UpdateMenuRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequestHelper {

    public static CreateMenuRequest randomRequest() {
        return randomRequest(UUID.randomUUID().toString());
    }

    public static UpdateMenuRequest updateMenuRequest(String businessId, String menuId, String shortUrlId) {
        CreateMenuRequest createMenuRequest = randomRequest(businessId);
        return UpdateMenuRequest.builder()
                .menuId(menuId)
                .shortUrlId(shortUrlId)
                .businessId(businessId)
                .sections(createMenuRequest.getSections())
                .build();
    }

    public static CreateMenuRequest randomRequest(String businessId) {
        return CreateMenuRequest.builder()
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
