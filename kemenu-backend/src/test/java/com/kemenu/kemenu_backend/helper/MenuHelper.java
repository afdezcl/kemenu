package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuHelper {

    public static Menu randomMenu() {
        return new Menu();
    }

    public static Menu from(String id, Map<String, MenuSection> sections) {
        return new Menu(id, sections);
    }
}
