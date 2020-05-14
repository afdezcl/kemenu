package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
public class CustomerWebController {

    private final JWTService jwtService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/customer/{email}")
    ResponseEntity<CustomerResponse> read(@RequestHeader(value = "Authorization") String token, @PathVariable String email) {
        String tokenEmail = jwtService.decodeAccessToken(token).getSubject();

        if (!email.equals(tokenEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return customerService.read(tokenEmail)
                .map(c -> ResponseEntity.ok(customerMapper.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }
}
