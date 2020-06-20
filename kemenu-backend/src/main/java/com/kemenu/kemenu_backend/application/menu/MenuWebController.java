package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class MenuWebController {

    private final JWTService jwtService;
    private final MenuService menuService;
    private final MenuMapper2 menuMapper2;

    @PostMapping("/menus")
    ResponseEntity<CreateMenuResponse> create(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid CreateMenuRequest createMenuRequest) {
        String customerEmail = jwtService.decodeAccessToken(token).getSubject();
        return menuService.create(customerEmail, createMenuRequest.getBusinessId(), menuMapper2.from(createMenuRequest))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/menus")
    ResponseEntity<UUID> update(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid UpdateMenuRequest updateMenuRequest) {
        String customerEmail = jwtService.decodeAccessToken(token).getSubject();
        return menuService.update(customerEmail, updateMenuRequest)
                .map(menuId -> ResponseEntity.ok(UUID.fromString(menuId)))
                .orElse(ResponseEntity.notFound().build());
    }
}
