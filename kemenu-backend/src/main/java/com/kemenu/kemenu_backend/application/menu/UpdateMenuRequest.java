package com.kemenu.kemenu_backend.application.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UpdateMenuRequest.UpdateMenuRequestBuilder.class)
public class UpdateMenuRequest {
    @NotBlank
    @Size(max = 255)
    String menuId;
    @NotBlank
    @Size(max = 255)
    String businessId;
    List<MenuSectionData> sections;
}
