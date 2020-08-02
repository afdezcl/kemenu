package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.business.BusinessMapper;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;
    private final ShortUrlRepository shortUrlRepository;
    private final BusinessMapper businessMapper;

    public Customer from(CustomerRequest customerRequest) {
        return new Customer(
                customerRequest.getEmail(),
                passwordEncoder.encode(customerRequest.getPassword()),
                customerRequest.getBusinessName()
        );
    }

    public CustomerResponse from(Customer customer, Locale locale) {
        String shortUrlId = shortUrlRepository.findByCustomerEmail(customer.getEmail())
                .map(ShortUrl::getId)
                .orElseGet(() -> "");
        return CustomerResponse.builder()
                .id(customer.getId())
                .businesses(businessMapper.from(shortUrlId, customer.getBusinesses(), locale))
                .newsletterStatus(customer.getMarketingInfo().getNewsletterStatus())
                .build();
    }
}
