package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMapper {

    public Menu from(MenuRequest menuRequest) {
        return null; // TODO: map the request to a menu.
    }
}
