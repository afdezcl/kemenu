package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public String create(Menu menu) {
        return menuRepository.create(menu);
    }

    public MenuResponse read(String menuId) {
        return menuRepository.findById(menuId)
                .map(menuMapper::from)
                .orElse(MenuResponse.builder().sections(List.of()).build());
    }
}
