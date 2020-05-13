package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.application.http.RefreshTokenRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequestHelper {

    public static RefreshTokenRequest from(String token) {
        return RefreshTokenRequest.builder()
                .refreshToken(token)
                .build();
    }
}
