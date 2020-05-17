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

    private final MenuMapper menuMapper;
    private final CustomerRepository customerRepository;
    private final ShortUrlRepository shortUrlRepository;

    public Optional<CreateMenuResponse> create(String customerEmail, String businessId, Menu menu) {
        return customerRepository.findByEmail(customerEmail)
                .flatMap(customer -> customer.createMenu(businessId, menu)
                        .flatMap(menuId -> {
                            customerRepository.save(customer);
                            return Optional.of(menuId);
                        })
                )
                .flatMap(menuId -> {
                    ShortUrl shortUrl = new ShortUrl(customerEmail, businessId, menuId);
                    String shortUrlId = shortUrlRepository.save(shortUrl);
                    return Optional.of(CreateMenuResponse.builder().menuId(menuId).shortUrlId(shortUrlId).build());
                });
    }

    public Optional<String> update(String customerEmail, UpdateMenuRequest updateMenuRequest) {
        return customerRepository.findByEmail(customerEmail)
                .flatMap(customer -> customer.changeMenu(updateMenuRequest.getBusinessId(), menuMapper.from(updateMenuRequest))
                        .flatMap(menuId -> {
                            customerRepository.save(customer);
                            return Optional.of(menuId);
                        })
                );
    }
}
