package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
class CustomerPublicController {

    private final CustomerService customerService;
    private final ObjectMapper mapper;

    @GetMapping("/{customerId}/businesses/{businessId}/menus/{menuId}")
    String readMenu(@PathVariable String customerId,
                    @PathVariable String businessId,
                    @PathVariable String menuId,
                    HttpServletResponse response) throws IOException {
        Optional<MenuResponse> optionalMenuResponse = customerService.readMenu(customerId, businessId, menuId);

        if (optionalMenuResponse.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        Cookie cookie = new Cookie("show_menu", Base64.getEncoder().encodeToString(mapper.writeValueAsString(optionalMenuResponse.get()).getBytes()));
        response.addCookie(cookie);
        return "forward:/index.html";
    }
}
