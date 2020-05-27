package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = DishRequest.DishRequestBuilder.class)
public class DishRequest {
    @NotBlank
    @Size(max = 255)
    String name;
    String description;
    @PositiveOrZero
    BigDecimal price;
    @Valid
    List<AllergenData> allergens;
    MultipartFile photo;
}
