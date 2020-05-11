package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerRequestHelper {

    public static CustomerRequest randomRequest() {
        return CustomerRequest.builder()
                .businessName(UUID.randomUUID().toString())
                .email("test@example.com")
                .password(UUID.randomUUID().toString())
                .recaptchaToken(UUID.randomUUID().toString())
                .build();
    }
}
