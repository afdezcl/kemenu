package com.kemenu.kemenu_backend.application.forgot_password;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ForgotPasswordResponse.ForgotPasswordResponseBuilder.class)
public class ForgotPasswordResponse {
    String id;
    String email;
}
