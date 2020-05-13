package com.kemenu.kemenu_backend.application.http;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RefreshTokenController {

    private final JWTService jwtService;

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        try {
            DecodedJWT decodedJWT = jwtService.decodeRefreshToken(refreshTokenRequest.getRefreshToken());
            String[] roles = jwtService.getRolesFrom(decodedJWT).stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .toArray(String[]::new);

            String accessToken = jwtService.generateAccessToken(decodedJWT.getSubject(), roles);
            String refreshToken = jwtService.generateRefreshToken(decodedJWT.getSubject(), roles);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + accessToken);
            responseHeaders.set("JWT-Refresh-Token", "Bearer " + refreshToken);
            return new ResponseEntity<>("", responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", null, HttpStatus.UNAUTHORIZED);
        }
    }
}
