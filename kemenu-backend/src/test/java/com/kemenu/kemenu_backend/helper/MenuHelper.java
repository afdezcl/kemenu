package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuHelper {

    public static Menu randomMenu() {
        return new Menu();
    }
}
