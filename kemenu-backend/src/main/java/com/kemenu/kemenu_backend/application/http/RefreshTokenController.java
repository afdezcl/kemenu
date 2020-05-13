package com.kemenu.kemenu_backend.application.http;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RefreshTokenController {

    private final JWTService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<UUID> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        try {
            DecodedJWT decodedJWT = jwtService.decodeRefreshToken(refreshTokenRequest.getRefreshToken());
        } catch (TokenExpiredException e) {

        }
        return null;
    }
}
