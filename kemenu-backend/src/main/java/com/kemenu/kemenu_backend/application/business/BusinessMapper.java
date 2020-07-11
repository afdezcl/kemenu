package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.domain.model.Business;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class BusinessMapper {

    private final MenuMapper menuMapper;

    public List<BusinessResponse> from(String shortUrlId, List<Business> businesses, Locale locale) {
        return businesses.stream().map(b -> from(shortUrlId, b, locale)).collect(toList());
    }

    public BusinessResponse from(String shortUrlId, Business business, Locale locale) {
        return BusinessResponse.builder()
                .id(business.getId())
                .name(business.getName())
                .menus(menuMapper.from(shortUrlId, business.getName(), business.getMenus(), locale))
                .imageUrl(business.getImageUrl())
                .phone(business.getPhone())
                .info(business.getInfo())
                .color(business.getColor())
                .build();
    }
}
