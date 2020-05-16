package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
        return new Menu(sections);
    }

    public Menu from(UpdateMenuRequest updateMenuRequest) {
        Menu menuWithoutId = from(
                CreateMenuRequest.builder()
                        .businessId(updateMenuRequest.getBusinessId())
                        .sections(updateMenuRequest.getSections())
                        .build()
        );

        return new Menu(updateMenuRequest.getMenuId(), menuWithoutId.getSections());
    }

    public List<MenuResponse> from(List<Menu> menus) {
        return menus.stream().map(this::from).collect(toList());
    }

    public MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
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
