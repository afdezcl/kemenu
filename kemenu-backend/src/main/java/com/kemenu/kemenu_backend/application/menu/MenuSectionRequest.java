package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = MenuSectionRequest.MenuSectionRequestBuilder.class)
public class MenuSectionRequest {
    @NotBlank
    @Size(max = 255)
    String name;
    @Valid
    List<DishRequest> dishes;
}
