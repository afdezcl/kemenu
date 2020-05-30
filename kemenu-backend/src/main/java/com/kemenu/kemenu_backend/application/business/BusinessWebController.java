package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class BusinessWebController {

    private final JWTService jwtService;
    private final CustomerService customerService;

    @PutMapping("/customer/{email}/business/{businessId}")
    ResponseEntity<UUID> changeBusiness(@RequestHeader(value = "Authorization") String token,
                                        @PathVariable String email,
                                        @PathVariable String businessId,
                                        @RequestBody @Valid UpdateBusinessRequest request) {
        String tokenEmail = jwtService.decodeAccessToken(token).getSubject();

        if (!email.equals(tokenEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return customerService.changeBusiness(email, businessId, request)
                .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                .orElse(ResponseEntity.notFound().build());
    }
}
