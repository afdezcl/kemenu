package com.kemenu.kemenu_backend.application.menu;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/menus")
class MenuPublicController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    MenuResponse read(@PathVariable String id) {
        return menuService.read(null, null, id);
    }
}
