package com.kemenu.kemenu_backend.application.allergen;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = AllergenData.AllergenDataBuilder.class)
public class AllergenData {
    @NotBlank
    @Size(max = 18)
    String id;
    @NotBlank
    @Size(max = 255)
    String name;
}
