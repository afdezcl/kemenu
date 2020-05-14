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

    public List<BusinessData> from(List<Business> businesses) {
        return businesses.stream().map(this::from).collect(toList());
    }

    public BusinessData from(Business business) {
        return BusinessData.builder()
                .name(business.getName())
                .menus(menuMapper.from(business.getMenus()))
                .build();
    }
}
