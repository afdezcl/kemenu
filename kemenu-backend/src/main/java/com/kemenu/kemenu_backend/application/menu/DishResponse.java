package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = DishResponse.DishResponseBuilder.class)
public class DishResponse {
    String name;
    String description;
    BigDecimal price;
    String formattedPrice;
    List<AllergenData> allergens;
    String imageUrl;
    boolean available;
}
