package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.validation.SamePassword;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@SamePassword
@Builder(toBuilder = true)
@JsonDeserialize(builder = PasswordChangeRequest.PasswordChangeRequestBuilder.class)
public class PasswordChangeRequest {
    @NotBlank
    @Size(max = 255)
    String password;
    @NotBlank
    @Size(max = 255)
    String repeatedPassword;
}
