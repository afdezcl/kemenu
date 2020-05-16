package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class MenuMapper {

    private final DishMapper dishMapper;
    private final ShortUrlRepository shortUrlRepository;

    public Menu from(CreateMenuRequest createMenuRequest) {
        List<MenuSection> sections = createMenuRequest.getSections().stream()
                .map(msd -> MenuSection.builder()
                        .name(msd.getName())
                        .dishes(dishMapper.from(msd))
                        .build()
                )
                .collect(toList());
        return new Menu(sections);
    }

    public Menu from(String menuId, UpdateMenuRequest updateMenuRequest) {
        Menu menuWithoutId = from(
                CreateMenuRequest.builder()
                        .businessId(updateMenuRequest.getBusinessId())
                        .sections(updateMenuRequest.getSections())
                        .build()
        );

        return new Menu(menuId, menuWithoutId.getSections());
    }

    public List<MenuResponse> from(String customerEmail, String businessName, List<Menu> menus) {
        return menus.stream().map(m -> from(customerEmail, businessName, m)).collect(toList());
    }

    public MenuResponse from(String customerEmail, String businessName, Menu menu) {
        String shortUrlId = shortUrlRepository.findByCustomerEmail(customerEmail)
                .map(ShortUrl::getId)
                .orElse("");
        return MenuResponse.builder()
                .id(menu.getId())
                .businessName(businessName)
                .shortUrlId(shortUrlId)
                .sections(menu.getSections().stream()
                        .map(ms -> MenuSectionData.builder()
                                .name(ms.getName())
                                .dishes(dishMapper.from(ms))
                                .build())
                        .collect(toList())
                )
                .build();
    }
}
