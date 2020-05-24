package com.kemenu.kemenu_backend.helper.customer;

import com.kemenu.kemenu_backend.application.customer.PasswordChangeRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordChangeRequestHelper {
    public static PasswordChangeRequest random() {
        String password = UUID.randomUUID().toString();
        return PasswordChangeRequest.builder()
                .password(password)
                .repeatedPassword(password)
                .build();
    }

    public static PasswordChangeRequest notSamePassword() {
        return PasswordChangeRequest.builder()
                .password(UUID.randomUUID().toString())
                .repeatedPassword(UUID.randomUUID().toString())
                .build();
    }
}
