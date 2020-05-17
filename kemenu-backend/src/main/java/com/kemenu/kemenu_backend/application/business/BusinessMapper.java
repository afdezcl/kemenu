package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.domain.model.Business;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class BusinessMapper {

    private final MenuMapper menuMapper;

    public List<BusinessData> from(String shortUrlId, List<Business> businesses) {
        return businesses.stream().map(b -> from(shortUrlId, b)).collect(toList());
    }

    public BusinessData from(String shortUrlId, Business business) {
        return BusinessData.builder()
                .id(business.getId())
                .name(business.getName())
                .menus(menuMapper.from(shortUrlId, business.getName(), business.getMenus()))
                .build();
    }
}
