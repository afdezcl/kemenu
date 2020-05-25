package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
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
@RequestMapping("/public/forgot/password")
class ForgotPasswordPublicController {

    private final CustomerRepository customerRepository;

    @GetMapping("/{customerId}")
    String confirm(@PathVariable String customerId, HttpServletResponse response) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    Cookie cookie = new Cookie("forgot_password_email", customer.getEmail());
                    response.addCookie(cookie);
                    return "forward:/index.html";
                }).orElse("forward:/index.html");
    }
}
