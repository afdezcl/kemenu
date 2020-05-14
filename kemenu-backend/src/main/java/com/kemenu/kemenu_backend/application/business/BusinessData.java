package com.kemenu.kemenu_backend.application.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = BusinessData.BusinessDataBuilder.class)
public class BusinessData {
    String name;
    List<MenuResponse> menus;
}
