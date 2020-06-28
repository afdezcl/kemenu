package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;
    private final CloudinaryService cloudinaryService;

    public List<MenuResponse> from(String shortUrlId, String businessName, List<Menu> menus) {
        return menus.stream().map(m -> from(shortUrlId, businessName, m)).collect(toList());
    }

    public MenuResponse from(String shortUrlId, String businessName, Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .businessName(businessName)
                .shortUrlId(shortUrlId)
                .sections(menu.getSections().stream()
                        .map(ms -> MenuSectionResponse.builder()
                                .name(ms.getName())
                                .dishes(dishMapper.from(ms))
                                .build())
                        .collect(toList())
                )
                .imageUrl(!StringUtils.isEmpty(menu.getImageUrl()) ? cloudinaryService.getOptimizedUrl(menu.getImageUrl()) : "")
                .build();
    }
}
