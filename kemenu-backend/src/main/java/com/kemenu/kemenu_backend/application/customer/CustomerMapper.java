package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.business.BusinessMapper;
import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;
    private final BusinessMapper businessMapper;

    public Customer from(CustomerRequest customerRequest) {
        return new Customer(
                customerRequest.getEmail(),
                passwordEncoder.encode(customerRequest.getPassword()),
                customerRequest.getBusinessName()
        );
    }

    public CustomerResponse from(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .businesses(businessMapper.from(customer.getBusinesses()))
                .build();
    }
}
