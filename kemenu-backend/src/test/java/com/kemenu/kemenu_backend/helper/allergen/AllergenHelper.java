package com.kemenu.kemenu_backend.helper.allergen;

import com.kemenu.kemenu_backend.domain.model.Allergen;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllergenHelper {

    public static Allergen random() {
        return Allergen.builder()
                .id(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .build();
    }
}
