package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = MenuSectionResponse.MenuSectionResponseBuilder.class)
public class MenuSectionResponse {
    String name;
    List<DishResponse> dishes;
}
