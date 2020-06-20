package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {

    private final CustomerRepository customerRepository;
    private final ShortUrlRepository shortUrlRepository;
    private final MenuRequestMapper menuRequestMapper;

    public Optional<CreateMenuResponse> create(String customerEmail, String businessId, Menu menu) {
        return customerRepository.findByEmail(customerEmail)
                .flatMap(customer -> customer.createMenu(businessId, menu)
                        .flatMap(menuId -> {
                            customerRepository.save(customer);
                            return Optional.of(menuId);
                        })
                )
                .flatMap(menuId -> {
                    Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByCustomerEmail(customerEmail);
                    ShortUrl shortUrl;

                    if (optionalShortUrl.isPresent()) {
                        shortUrl = optionalShortUrl.get();
                        shortUrl.addMenu(menuId);
                    } else {
                        shortUrl = new ShortUrl(customerEmail, businessId, menuId);
                    }

                    String shortUrlId = shortUrlRepository.save(shortUrl);
                    return Optional.of(CreateMenuResponse.builder().menuId(menuId).shortUrlId(shortUrlId).build());
                });
    }

    public Optional<String> update(String customerEmail, UpdateMenuRequest updateMenuRequest) {
        return customerRepository.findByEmail(customerEmail)
                .flatMap(customer -> customer.changeMenu(updateMenuRequest.getBusinessId(), menuRequestMapper.from(updateMenuRequest))
                        .flatMap(menuId -> {
                            customerRepository.save(customer);
                            return Optional.of(menuId);
                        })
                );
    }
}
