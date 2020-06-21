package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.business.BusinessRequestMapper;
import com.kemenu.kemenu_backend.application.business.UpdateBusinessRequest;
import com.kemenu.kemenu_backend.application.email.EmailEventFactory;
import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final EventPublisher eventPublisher;
    private final EmailEventFactory emailEventFactory;
    private final ShortUrlRepository shortUrlRepository;
    private final MenuMapper menuMapper;
    private final PasswordEncoder passwordEncoder;
    private final BusinessRequestMapper businessRequestMapper;

    public String create(CustomerRequest customerRequest) {
        Customer customer = customerMapper.from(customerRequest);
        String customerId = customerRepository.save(customer);
        eventPublisher.publish(emailEventFactory.confirmationEmailEvent(customerRequest.getLang(), customerRequest.getEmail()));
        return customerId;
    }

    public Optional<Customer> read(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> all() {
        return customerRepository.all();
    }

    @Deprecated(forRemoval = true) // In favor of readMenus method
    public Optional<MenuResponse> readMenu(String shortUrlId) {
        return shortUrlRepository.findById(shortUrlId)
                .flatMap(shortUrl -> customerRepository.findByEmail(shortUrl.getCustomerEmail())
                        .flatMap(customer -> customer.findBusiness(shortUrl.getBusinessId())
                                .flatMap(business -> business.findMenu(shortUrl.getMenus().get(0))
                                        .flatMap(menu -> Optional.of(menuMapper.from(shortUrl.getId(), business.getName(), menu)))
                                )
                        )
                );
    }

    public Optional<List<MenuResponse>> readMenus(String shortUrlId) {
        return shortUrlRepository.findById(shortUrlId)
                .flatMap(shortUrl -> customerRepository.findByEmail(shortUrl.getCustomerEmail())
                        .flatMap(customer -> customer.findBusiness(shortUrl.getBusinessId())
                                .flatMap(business -> Optional.of(business.getMenus().stream()
                                        .map(menu -> menuMapper.from(shortUrl.getId(), business.getName(), menu))
                                        .collect(toList()))
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

    public Optional<String> changeBusiness(String email, String businessId, UpdateBusinessRequest updateBusinessRequest) {
        return customerRepository.findByEmail(email)
                .flatMap(c -> c.findBusiness(businessId)
                        .flatMap(b -> {
                            Business newBusiness = businessRequestMapper.from(businessId, b.getMenus(), updateBusinessRequest);
                            return c.changeBusiness(newBusiness);
                        })
                        .flatMap(updatedBusinessId -> Optional.of(customerRepository.save(c)))
                );
    }
}
