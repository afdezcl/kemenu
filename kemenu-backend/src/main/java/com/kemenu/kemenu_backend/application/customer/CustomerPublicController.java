package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
class CustomerPublicController {

    private final CustomerService customerService;
    private final ObjectMapper mapper;

    @GetMapping("/{customerId}/businesses/{businessId}/menus/{menuId}")
    ResponseEntity<MenuResponse> readMenu(@PathVariable String customerId,
                                          @PathVariable String businessId,
                                          @PathVariable String menuId,
                                          HttpServletResponse response) throws JsonProcessingException {
        Optional<MenuResponse> optionalMenuResponse = customerService.readMenu(customerId, businessId, menuId);

        if (optionalMenuResponse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cookie cookie = new Cookie("show_menu", mapper.writeValueAsString(optionalMenuResponse.get()));
        response.addCookie(cookie);
        // TODO: redirect user to /show/menu

        return optionalMenuResponse
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
