package com.kemenu.kemenu_backend.application.customer;

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
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/show")
class CustomerPublicController {

    private final CustomerService customerService;

    @GetMapping("/{shortUrlId}")
    String readMenu(@PathVariable String shortUrlId, Locale locale, HttpServletResponse response) throws IOException {
        Optional<List<MenuResponse>> optionalMenuResponses = customerService.readMenus(shortUrlId, locale);

        if (optionalMenuResponses.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        Cookie cookie = new Cookie("show_menu", Base64.getEncoder().encodeToString(shortUrlId.getBytes()));
        response.addCookie(cookie);
        log.info("Showing {} shortUrl", shortUrlId);
        return "forward:/index.html";
    }
}
