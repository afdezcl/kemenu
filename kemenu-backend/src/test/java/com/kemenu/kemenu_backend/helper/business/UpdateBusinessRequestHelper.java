package com.kemenu.kemenu_backend.helper.business;

import com.kemenu.kemenu_backend.application.business.UpdateBusinessRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBusinessRequestHelper {

    public static UpdateBusinessRequest random() {
        return UpdateBusinessRequest.builder()
                .name(UUID.randomUUID().toString())
                .imageUrl(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString())
                .info(UUID.randomUUID().toString())
                .color("#FfFFFF")
                .build();
    }
}
