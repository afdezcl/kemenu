package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.customer.CustomerMapper;
import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<UUID> create(@RequestBody CustomerRequest customerRequest) {
        try {
            return ResponseEntity.ok(UUID.fromString(customerService.create(customerMapper.from(customerRequest))));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
