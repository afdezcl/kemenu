package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.security.IntrospectiveService;
import lombok.AllArgsConstructor;
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

    private final IntrospectiveService introspectiveService;
    private final CustomerService customerService;

    @PutMapping("/customer/{email}/business/{businessId}")
    ResponseEntity<UUID> changeBusiness(@RequestHeader(value = "Authorization") String token,
                                        @PathVariable String email,
                                        @PathVariable String businessId,
                                        @RequestBody @Valid UpdateBusinessRequest request) {
        return introspectiveService.doCallOnMe(token, email, () -> customerService
                .changeBusiness(email, businessId, request)
                .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                .orElseGet(() -> ResponseEntity.notFound().build())
        );
    }
}
