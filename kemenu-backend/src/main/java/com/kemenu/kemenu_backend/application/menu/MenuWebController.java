package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
public class MenuWebController {

    private final JWTService jwtService;
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @PostMapping("/menus")
    ResponseEntity<UUID> create(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid MenuRequest menuRequest) {
        String customerEmail = jwtService.decodeAccessToken(token).getSubject();
        return menuService.create(customerEmail, menuRequest.getBusinessId(), menuMapper.from(menuRequest))
                .map(menuId -> ResponseEntity.ok(UUID.fromString(menuId)))
                .orElse(ResponseEntity.notFound().build());
    }
}
