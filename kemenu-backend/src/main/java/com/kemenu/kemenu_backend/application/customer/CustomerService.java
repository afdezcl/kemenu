package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
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
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findById(shortUrlId);

        if (optionalShortUrl.isEmpty()) {
            return Optional.empty();
        }

        ShortUrl shortUrl = optionalShortUrl.get();
        Optional<Customer> optionalCustomer = customerRepository.findById(shortUrl.getCustomerId());

        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Optional<Business> optionalBusiness = customer.findBusiness(shortUrl.getBusinessId());

        if (optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        Business business = optionalBusiness.get();

        Optional<Menu> optionalMenu = business.findMenu(shortUrl.getMenuId());

        if (optionalMenu.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(menuMapper.from(business.getName(), optionalMenu.get()));
    }
}
