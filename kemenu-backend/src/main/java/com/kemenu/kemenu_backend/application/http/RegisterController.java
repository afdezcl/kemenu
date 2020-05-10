package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.customer.CustomerMapper;
import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final Recaptcha recaptcha;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<UUID> create(@RequestBody @Valid CustomerRequest customerRequest) {
        try {
            if (recaptcha.isValid(customerRequest.getRecaptchaToken())) {
                return ResponseEntity.ok(UUID.fromString(customerService.create(customerMapper.from(customerRequest))));
            }
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
