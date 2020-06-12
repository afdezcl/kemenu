package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.menu.DishRequest;
import com.kemenu.kemenu_backend.application.menu.MenuSectionRequest;
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

    public static UpdateMenuRequest updateMenuRequest(String businessId, String menuId) {
        CreateMenuRequest createMenuRequest = randomRequest(businessId);
        return UpdateMenuRequest.builder()
                .menuId(menuId)
                .businessId(businessId)
                .sections(createMenuRequest.getSections())
                .build();
    }

    public static CreateMenuRequest randomRequest(String businessId) {
        return CreateMenuRequest.builder()
                .businessId(businessId)
                .sections(List.of(MenuSectionRequest.builder()
                        .name(UUID.randomUUID().toString())
                        .dishes(List.of(DishRequest.builder()
                                .name(UUID.randomUUID().toString())
                                .description(UUID.randomUUID().toString())
                                .price(BigDecimal.TEN)
                                .allergens(List.of(AllergenData.builder()
                                        .id(UUID.randomUUID().toString().substring(0, 18))
                                        .name(UUID.randomUUID().toString())
                                        .build()))
                                .imageUrl(UUID.randomUUID().toString())
                                .available(true)
                                .build()))
                        .build()))
                .build();
    }
}
