package com.kemenu.kemenu_backend.helper.business;

import com.kemenu.kemenu_backend.domain.model.Business;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessHelper {

    public static Business randomBusiness() {
        return new Business(UUID.randomUUID().toString());
    }
}
