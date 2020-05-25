package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.email.EmailService;
import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final EventPublisher eventPublisher;
    private final EmailService emailService;
    private final ShortUrlRepository shortUrlRepository;
    private final MenuMapper menuMapper;
    private final PasswordEncoder passwordEncoder;

    public String create(CustomerRequest customerRequest) {
        Customer customer = customerMapper.from(customerRequest);
        String customerId = customerRepository.save(customer);
        eventPublisher.publish(emailService.confirmationEmailEvent(customerRequest.getLang(), customerRequest.getEmail()));
        return customerId;
    }

    public Optional<Customer> read(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> all() {
        return customerRepository.all();
    }

    public Optional<MenuResponse> readMenu(String shortUrlId) { // TODO: (1) add the menuId you want to read
        return shortUrlRepository.findById(shortUrlId)
                .flatMap(shortUrl -> customerRepository.findByEmail(shortUrl.getCustomerEmail())
                        .flatMap(customer -> customer.findBusiness(shortUrl.getBusinessId()) // TODO: (2) Change to findMenu method when TODO (1)
                                .flatMap(business -> business.findMenu(shortUrl.getMenus().get(0)) // TODO: (3) Change to findMenu method when TODO (1)
                                        .flatMap(menu -> Optional.of(menuMapper.from(shortUrl.getId(), business.getName(), menu)))
                                )
                        )
                );
    }

    public Optional<String> changePassword(String email, String password) {
        return customerRepository.findByEmail(email)
                .flatMap(c -> {
                    c.changePassword(passwordEncoder.encode(password));
                    return Optional.of(customerRepository.save(c));
                });
    }

    public Optional<String> changeBusinessName(String email, String businessId, String newBusinessName) {
        return customerRepository.findByEmail(email)
                .flatMap(c -> c.changeBusinessName(businessId, newBusinessName)
                        .flatMap(newName -> Optional.of(customerRepository.save(c)))
                );
    }
}
