package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<UUID> create(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerMapper.from(customerRequest);

        if (customerService.exists(customer)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.ok(UUID.fromString(customerService.create(customer)));
    }
}
