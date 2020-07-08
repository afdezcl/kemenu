package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuHelper {

    public static Menu randomMenu() {
        return Menu.builder()
                .id(UUID.randomUUID().toString())
                .sections(IntStream.rangeClosed(0, 3) // 4 sections
                        .mapToObj(i -> MenuSectionHelper.randomSection())
                        .collect(toList())
                )
                .build();
    }

    public static Menu from(String id, List<MenuSection> sections) {
        return Menu.builder().id(id).sections(sections).build();
    }
}
