package com.kemenu.kemenu_backend.application.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = RefreshTokenRequest.RefreshTokenRequestBuilder.class)
public class RefreshTokenRequest {
    @NotBlank
    @Size(max = 4096)
    String refreshToken;
}
