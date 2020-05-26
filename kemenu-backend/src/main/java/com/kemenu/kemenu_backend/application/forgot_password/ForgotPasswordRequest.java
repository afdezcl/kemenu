package com.kemenu.kemenu_backend.application.forgot_password;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ForgotPasswordRequest.ForgotPasswordRequestBuilder.class)
public class ForgotPasswordRequest {
    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    String email;
    @NotBlank
    String recaptchaToken;
    @NotBlank
    @Size(max = 2)
    String lang;
}
