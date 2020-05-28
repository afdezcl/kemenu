package com.kemenu.kemenu_backend.helper.business;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessHelper {

    public static Business randomFrom(String businessId, List<Menu> menus) {
        return new Business(
                businessId,
                UUID.randomUUID().toString(),
                menus,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }
}
