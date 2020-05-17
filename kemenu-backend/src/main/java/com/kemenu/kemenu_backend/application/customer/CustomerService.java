package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ShortUrlRepository shortUrlRepository;
    private final MenuMapper menuMapper;

    public String create(Customer customer) {
        return customerRepository.save(customer); // TODO: Send event to send email
    }

    public Optional<Customer> read(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> all() {
        return customerRepository.all();
    }

    public Optional<MenuResponse> readMenu(String shortUrlId) {
        return shortUrlRepository.findById(shortUrlId)
                .flatMap(shortUrl -> customerRepository.findByEmail(shortUrl.getCustomerEmail())
                        .flatMap(customer -> customer.findBusiness(shortUrl.getBusinessId())
                                .flatMap(business -> business.findMenu(shortUrl.getMenuId())
                                        .flatMap(menu -> Optional.of(menuMapper.from(shortUrl.getId(), business.getName(), menu)))
                                )
                        )
                );
    }
}
