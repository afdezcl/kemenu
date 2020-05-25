package com.kemenu.kemenu_backend.application.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = BusinessChangeNameRequest.BusinessChangeNameRequestBuilder.class)
public class BusinessChangeNameRequest {
    @NotBlank
    @Size(max = 255)
    String newName;
}
