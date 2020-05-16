package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final CustomerRepository customerRepository;
    private final ShortUrlRepository shortUrlRepository;

    public Optional<String> create(String customerEmail, String businessId, Menu menu) {
        return save(
                customerEmail,
                businessId,
                (c, b) -> c.createMenu(b, menu),
                true
        );
    }

    public Optional<String> update(String customerEmail, UpdateMenuRequest updateMenuRequest) {
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findById(updateMenuRequest.getShortUrlId());

        if (optionalShortUrl.isEmpty()) {
            return Optional.empty();
        }

        return save(
                customerEmail,
                updateMenuRequest.getBusinessId(),
                (c, b) -> {
                    c.changeMenu(b, menuMapper.from(optionalShortUrl.get().getMenuId(), updateMenuRequest));
                    return updateMenuRequest.getShortUrlId();
                },
                false
        );
    }

    private Optional<String> save(String customerEmail,
                                  String businessId,
                                  BiFunction<Customer, Business, String> action,
                                  boolean saveShortUrl) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);

        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Optional<Business> optionalBusiness = customer.findBusiness(businessId);

        if (optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        Business business = optionalBusiness.get();
        String menuId = action.apply(customer, business);

        customerRepository.save(customer);

        if (saveShortUrl) {
            ShortUrl shortUrl = new ShortUrl(customer.getEmail(), business.getId(), menuId);
            String shortUrlId = shortUrlRepository.save(shortUrl);
            return Optional.of(shortUrlId);
        }

        return Optional.of(menuId);
    }
}
