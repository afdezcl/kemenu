package com.kemenu.kemenu_backend.application.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.validation.HexColor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UpdateBusinessRequest.UpdateBusinessRequestBuilder.class)
public class UpdateBusinessRequest {
    @NotBlank
    @Size(max = 255)
    String name;
    String imageUrl;
    String phone;
    String info;
    @HexColor
    String color;
}
