package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UpdateMenuResponse.UpdateMenuResponseBuilder.class)
public class UpdateMenuResponse {
    List<MenuSectionResponse> sections;
    String name;
    String customerEmail;
    String shortUrlId;
    String imageUrl;
    String id;
    String currency;
}
