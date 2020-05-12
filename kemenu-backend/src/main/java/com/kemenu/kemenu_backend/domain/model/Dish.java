package com.kemenu.kemenu_backend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Dish {
    String name;
    String description;
    BigDecimal price;
    List<Allergen> allergens;
}
