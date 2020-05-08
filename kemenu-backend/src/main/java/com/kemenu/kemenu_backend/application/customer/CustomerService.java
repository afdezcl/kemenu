package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public UUID create(Customer customer) {
        return customerRepository.create(customer); // TODO: Send event to send email
    }
}
