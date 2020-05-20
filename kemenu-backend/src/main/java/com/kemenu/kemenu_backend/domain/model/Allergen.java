package com.kemenu.kemenu_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Allergen {
    String id;
    String name;
}
