package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerRequest.CustomerRequestBuilder.class)
public class CustomerRequest {
    @NotBlank
    @Size(max = 255)
    String businessName;
    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    String email;
    @NotBlank
    @Size(min = 8, max = 255)
    String password;
    @NotBlank
    String recaptchaToken;
    @NotBlank
    @Size(max = 2)
    String lang;
}
