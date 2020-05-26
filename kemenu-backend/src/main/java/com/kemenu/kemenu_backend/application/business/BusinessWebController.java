package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class BusinessWebController {

    private final JWTService jwtService;
    private final CustomerService customerService;

    @PatchMapping("/customer/{email}/business/{businessId}")
    ResponseEntity<UUID> changeBusinessName(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String email,
                                            @PathVariable String businessId,
                                            @RequestBody @Valid BusinessChangeNameRequest request) {
        String tokenEmail = jwtService.decodeAccessToken(token).getSubject();

        if (!email.equals(tokenEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return customerService.changeBusinessName(email, businessId, request.getNewName())
                .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customer/{email}/business/{businessId}/upload/photo")
    ResponseEntity<UUID> changeBusinessPhoto(@RequestHeader(value = "Authorization") String token,
                                             @PathVariable String email,
                                             @PathVariable String businessId,
                                             @RequestParam("file") MultipartFile file) {
        // TODO: Implement
        return null;
    }
}
