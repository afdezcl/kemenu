package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public")
class MenuPublicController {

    private final CustomerService customerService;

    @Deprecated(forRemoval = true)
    @GetMapping("/short/{shortUrlId}")
    ResponseEntity<MenuResponse> readMenu(@PathVariable String shortUrlId) {
        return customerService.readMenu(shortUrlId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/v1/short/{shortUrlId}")
    ResponseEntity<List<MenuResponse>> readMenus(@PathVariable String shortUrlId) {
        return customerService.readMenus(shortUrlId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
