package com.kemenu.kemenu_backend.helper.forgot_password;

import com.kemenu.kemenu_backend.application.forgot_password.ForgotPasswordChangeRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ForgotPasswordChangeRequestHelper {

    public static ForgotPasswordChangeRequest random() {
        String password = UUID.randomUUID().toString();
        return ForgotPasswordChangeRequest.builder()
                .password(password)
                .repeatedPassword(password)
                .recaptchaToken(UUID.randomUUID().toString())
                .build();
    }

    public static ForgotPasswordChangeRequest notSamePassword() {
        return ForgotPasswordChangeRequest.builder()
                .password(UUID.randomUUID().toString())
                .repeatedPassword(UUID.randomUUID().toString())
                .recaptchaToken(UUID.randomUUID().toString())
                .build();
    }
}
