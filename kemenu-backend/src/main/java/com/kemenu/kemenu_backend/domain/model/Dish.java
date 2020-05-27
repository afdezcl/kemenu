package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Dish {
    String name;
    String description;
    BigDecimal price;
    List<Allergen> allergens;
    String imageUrl;
}
