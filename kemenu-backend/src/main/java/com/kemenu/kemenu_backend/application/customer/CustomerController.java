package com.kemenu.kemenu_backend.application.customer;

import lombok.AllArgsConstructor;
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
    public @ResponseBody UUID create(@RequestBody CustomerRequest customerRequest) {
        return UUID.fromString(customerService.create(customerMapper.from(customerRequest)));
    }
}
