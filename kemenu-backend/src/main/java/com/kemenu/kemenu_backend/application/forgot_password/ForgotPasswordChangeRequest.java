package com.kemenu.kemenu_backend.application.forgot_password;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.validation.SamePassword;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@SamePassword
@Builder(toBuilder = true)
@JsonDeserialize(builder = ForgotPasswordChangeRequest.ForgotPasswordChangeRequestBuilder.class)
public class ForgotPasswordChangeRequest {
    @NotBlank
    @Size(max = 255)
    String password;
    @NotBlank
    @Size(max = 255)
    String repeatedPassword;
    @NotBlank
    String recaptchaToken;
}
