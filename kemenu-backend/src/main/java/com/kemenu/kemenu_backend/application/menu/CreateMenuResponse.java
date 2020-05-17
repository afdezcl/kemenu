package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CreateMenuResponse.CreateMenuResponseBuilder.class)
public class CreateMenuResponse {
    String menuId;
    String shortUrlId;
}
