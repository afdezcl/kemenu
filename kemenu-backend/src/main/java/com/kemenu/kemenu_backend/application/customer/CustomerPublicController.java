package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/show")
class CustomerPublicController {

    private final CustomerService customerService;
    private final ObjectMapper mapper;

    @GetMapping("/{shortUrlId}")
    String readMenu(@PathVariable String shortUrlId, HttpServletResponse response) throws IOException {
        Optional<MenuResponse> optionalMenuResponse = customerService.readMenu(shortUrlId);

        if (optionalMenuResponse.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        String menuResponseJson = mapper.writeValueAsString(optionalMenuResponse.get());
        log.info("SHOW MENU: {}", menuResponseJson);
        String menuResponseInBase64 = Base64.getEncoder().encodeToString(menuResponseJson.getBytes());
        log.info("SHOW MENU COOKIE: {}", menuResponseInBase64);
        Cookie cookie = new Cookie("show_menu", menuResponseInBase64);
        response.addCookie(cookie);
        return "forward:/index.html";
    }
}
