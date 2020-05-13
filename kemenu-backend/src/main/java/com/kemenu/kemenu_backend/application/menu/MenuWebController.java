package com.kemenu.kemenu_backend.application.menu;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
public class MenuWebController {

    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @PostMapping("/menus")
    public ResponseEntity<UUID> create(@RequestBody @Valid MenuRequest menuRequest) {
        return ResponseEntity.ok(UUID.fromString(menuService.create(menuMapper.from(menuRequest))));
    }
}
