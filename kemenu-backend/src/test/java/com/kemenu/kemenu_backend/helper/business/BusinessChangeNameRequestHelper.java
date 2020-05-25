package com.kemenu.kemenu_backend.helper.business;

import com.kemenu.kemenu_backend.application.business.BusinessChangeNameRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessChangeNameRequestHelper {

    public static BusinessChangeNameRequest random() {
        return BusinessChangeNameRequest.builder()
                .newName(UUID.randomUUID().toString())
                .build();
    }
}
