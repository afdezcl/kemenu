package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
class CustomerPublicController {

    private final CustomerService customerService;
    private final MenuMapper menuMapper;

    @GetMapping("/{customerId}/businesses/{businessId}/menus/{menuId}")
    ResponseEntity<MenuResponse> readMenu(@PathVariable String customerId,
                                          @PathVariable String businessId,
                                          @PathVariable String menuId) {
        return customerService.readMenu(customerId, businessId, menuId)
                .map(menu -> ResponseEntity.ok(menuMapper.from(menu)))
                .orElse(ResponseEntity.notFound().build());
    }
}
