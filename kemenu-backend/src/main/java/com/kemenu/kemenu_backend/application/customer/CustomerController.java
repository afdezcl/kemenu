package com.kemenu.kemenu_backend.application.customer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class CustomerController {

    @GetMapping("/customers")
    public String listAllCustomers() {
        return "Hola";
    }
}
