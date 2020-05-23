package com.kemenu.kemenu_backend.application.email;

import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/public/confirm/email")
class ConfirmedEmailController {

    private final ConfirmedEmailRepository confirmedEmailRepository;

    @GetMapping("/{confirmEmailId}")
    String confirm(@PathVariable String confirmEmailId, HttpServletResponse response) {
        return confirmedEmailRepository.findById(confirmEmailId)
                .map(confirmedEmail -> {
                    confirmedEmail.confirm();
                    confirmedEmailRepository.save(confirmedEmail);
                    boolean confirmed = confirmedEmail.isConfirmed();
                    if (confirmed) {
                        log.info("Email {} confirmed successfully", confirmedEmail.getEmail());
                    } else {
                        log.info("A customer with email {} tried to confirm an expired confirmation", confirmedEmail.getEmail());
                    }
                    Cookie cookie = new Cookie("confirmed_email", Boolean.toString(confirmed));
                    response.addCookie(cookie);
                    return "forward:/index.html";
                }).orElse("forward:/index.html");
    }
}
