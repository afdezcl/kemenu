package com.kemenu.kemenu_backend.helper.forgot_password;

import com.kemenu.kemenu_backend.application.forgot_password.ForgotPasswordRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ForgotPasswordRequestHelper {

    public static ForgotPasswordRequest random(String email) {
        return ForgotPasswordRequest.builder()
                .email(email)
                .recaptchaToken(UUID.randomUUID().toString())
                .lang("es")
                .build();
    }
}
