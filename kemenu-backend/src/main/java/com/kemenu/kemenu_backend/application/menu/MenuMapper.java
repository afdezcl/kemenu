package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;

    public Menu from(CreateMenuRequest createMenuRequest) {
        List<MenuSection> sections = createMenuRequest.getSections().stream()
                .map(msd -> MenuSection.builder()
                        .name(msd.getName())
                        .dishes(dishMapper.from(msd))
                        .build()
                )
                .collect(toList());
        return new Menu(sections, isNull(createMenuRequest.getImageUrl()) ? "" : createMenuRequest.getImageUrl());
    }

    public Menu from(UpdateMenuRequest updateMenuRequest) {
        Menu menuWithoutId = from(
                CreateMenuRequest.builder()
                        .businessId(updateMenuRequest.getBusinessId())
                        .sections(updateMenuRequest.getSections())
                        .imageUrl(updateMenuRequest.getImageUrl())
                        .build()
        );

        return new Menu(updateMenuRequest.getMenuId(), menuWithoutId.getSections(), menuWithoutId.getImageUrl());
    }

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
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
