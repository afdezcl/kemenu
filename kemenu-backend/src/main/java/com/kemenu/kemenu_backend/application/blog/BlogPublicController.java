package com.kemenu.kemenu_backend.application.blog;

import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/public/blogs")
public class BlogPublicController {

    @GetMapping("/{readableId}")
    String find(@PathVariable String readableId, Locale locale, HttpServletResponse response) throws IOException {
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
