package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/v1")
public class AdminCustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> listAllCustomers() {
        return customerService.all();
    }
}
