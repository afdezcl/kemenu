package com.kemenu.kemenu_backend.application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.auth0.jwt.interfaces.DecodedJWT;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JWTService {

    private static final int FIFTEEN_MIN = 900000;
    private static final int FOUR_HOURS = 1000 * 60 * 60 * 4;

    @Value("${app.secret}")
    private String appSecret;
    @Value("${app.refresh}")
    private String appRefresh;

    public String generateAccessToken(String username, String[] roles) {
        return generate(username, FIFTEEN_MIN, roles, appSecret);
    }

    public String generateAccessToken(String username, int expirationInMillis, String[] roles) {
        return generate(username, expirationInMillis, roles, appSecret);
    }

    public String generateRefreshToken(String username, String[] roles) {
        return generate(username, FOUR_HOURS, roles, appRefresh);
    }

    public String generateRefreshToken(String username, int expirationInMillis, String[] roles) {
        return generate(username, expirationInMillis, roles, appRefresh);
    }

    public DecodedJWT decodeAccessToken(String accessToken) {
        return decode(appSecret, accessToken);
    }

    public DecodedJWT decodeRefreshToken(String refreshToken) {
        return decode(appRefresh, refreshToken);
    }

    public List<SimpleGrantedAuthority> getRolesFrom(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("role").asList(String.class).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    private String generate(String username, int expirationInMillis, String[] roles, String signature) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationInMillis))
                .withArrayClaim("role", roles)
                .sign(HMAC512(signature.getBytes()));
    }

    private DecodedJWT decode(String signature, String token) {
        return JWT.require(Algorithm.HMAC512(signature.getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""));
    }
}
