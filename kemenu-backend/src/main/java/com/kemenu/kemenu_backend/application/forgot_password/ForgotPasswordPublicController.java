package com.kemenu.kemenu_backend.application.forgot_password;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/public/forgot/password")
class ForgotPasswordPublicController {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper mapper;

    @GetMapping("/{forgotPasswordId}")
    String confirm(@PathVariable String forgotPasswordId, HttpServletResponse response) {
        return forgotPasswordRepository.findById(forgotPasswordId)
                .map(forgotPassword -> customerRepository.findByEmail(forgotPassword.getEmail())
                        .map(customer -> {
                            try {
                                ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse(forgotPasswordId, customer.getEmail());
                                String jsonResponse = mapper.writeValueAsString(forgotPasswordResponse);
                                Cookie cookie = new Cookie("forgot_password_email", Base64.getEncoder().encodeToString(jsonResponse.getBytes()));
                                response.addCookie(cookie);
                                return "forward:/index.html";
                            } catch (JsonProcessingException e) {
                                return "forward:/index.html";
                            }
                        }).orElseGet(() -> "forward:/index.html")
                ).orElseGet(() -> "forward:/index.html");
    }
}
