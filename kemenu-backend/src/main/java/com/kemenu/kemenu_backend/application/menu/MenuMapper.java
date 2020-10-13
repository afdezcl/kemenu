package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;
    private final CloudinaryService cloudinaryService;
    private final CustomerRepository customerRepository;
    private final ShortUrlRepository shortUrlRepository;

    public List<MenuResponse> from(String shortUrlId, String businessName, List<Menu> menus, Locale locale) {
        return menus.stream().map(m -> from(shortUrlId, businessName, m, locale)).collect(toList());
    }

    public MenuResponse from(String shortUrlId, String businessName, Menu menu, Locale locale) {
        return MenuResponse.builder()
                .id(menu.getId())
                .businessName(businessName)
                .shortUrlId(shortUrlId)
                .sections(menu.getSections().stream()
                        .map(ms -> MenuSectionResponse.builder()
                                .name(ms.getName())
                                .dishes(dishMapper.from(ms, menu.getCurrency(), locale))
                                .build())
                        .collect(toList())
                )
                .imageUrl(!StringUtils.isEmpty(menu.getImageUrl()) ? cloudinaryService.getOptimizedUrl(menu.getImageUrl()) : "")
                .currency(menu.getCurrency().getCurrencyCode())
                .name(menu.getName())
                .build();
    }

    // TODO: REFACTOR ME PLS
    public Optional<UpdateMenuResponse> toUpdate(String customerEmail,
                                                 String businessId,
                                                 Menu menu,
                                                 Locale locale) {
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
        return shortUrlRepository.findByCustomerEmail(customerEmail)
                .flatMap(shortUrl -> Optional.ofNullable(shortUrl.getId()))
                .flatMap(shortUrlId -> Optional.ofNullable(from(shortUrlId, business.getName(), menu, locale)))
                .flatMap(menuResponse -> Optional.ofNullable(UpdateMenuResponse.builder()
                        .sections(menuResponse.getSections())
                        .name(menuResponse.getName())
                        .customerEmail(customerEmail)
                        .shortUrlId(menuResponse.getShortUrlId())
                        .imageUrl(menuResponse.getImageUrl())
                        .id(menuResponse.getId())
                        .currency(menuResponse.getCurrency())
                        .build())
                );
    }
}
