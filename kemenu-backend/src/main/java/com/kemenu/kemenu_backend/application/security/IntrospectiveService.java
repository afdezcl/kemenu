package com.kemenu.kemenu_backend.application.security;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class IntrospectiveService {

    private final JWTService jwtService;

    public <T> ResponseEntity<T> doCallOnMe(String token, String email, Supplier<ResponseEntity<T>> callToDo) {
        String tokenEmail = jwtService.decodeAccessToken(token).getSubject();

        if (!email.equals(tokenEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return callToDo.get();
    }
}
