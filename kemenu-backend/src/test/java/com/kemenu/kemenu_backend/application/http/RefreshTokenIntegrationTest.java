package com.kemenu.kemenu_backend.application.http;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.helper.RefreshTokenRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefreshTokenIntegrationTest extends KemenuIntegrationTest {

    @Test
    void aCustomerCouldRefreshAToken() {
        HttpHeaders headers = webTestClient
                .post().uri("/refresh")
                .body(Mono.just(RefreshTokenRequestHelper.from(generateRefreshToken())), RefreshTokenRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Authorization")
                .expectHeader().exists("JWT-Refresh-Token")
                .expectBody().returnResult().getResponseHeaders();

        String newAccessToken = headers.get("Authorization").get(0);
        String newRefreshToken = headers.get("JWT-Refresh-Token").get(0);

        DecodedJWT decodedAccessToken = jwtService.decodeAccessToken(newAccessToken);
        DecodedJWT decodedRefreshToken = jwtService.decodeRefreshToken(newRefreshToken);

        assertEquals("testusername", decodedAccessToken.getSubject());
        assertEquals("testusername", decodedRefreshToken.getSubject());
    }

    @Test
    void whenACustomerTryToRefreshTokenWithAnExpiredOneShouldFails() {
        webTestClient
                .post().uri("/refresh")
                .body(Mono.just(RefreshTokenRequestHelper.from(generateExpiredRefreshToken())), RefreshTokenRequest.class)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().doesNotExist("Authorization")
                .expectHeader().doesNotExist("JWT-Refresh-Token");
    }
}