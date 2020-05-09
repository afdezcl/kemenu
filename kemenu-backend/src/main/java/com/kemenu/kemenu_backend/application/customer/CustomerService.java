package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.domain.model.BusinessRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;

    public String create(Customer customer) {
        businessRepository.create(customer.getFirstBusiness());
        return customerRepository.create(customer); // TODO: Send event to send email
    }
}
