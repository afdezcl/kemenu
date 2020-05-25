package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.application.security.Recaptcha;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
class ForgotPasswordController {

    private final Recaptcha recaptcha;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot/password")
    ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        if (recaptcha.isValid(request.getRecaptchaToken())) {
            forgotPasswordService.sendForgotPasswordEmail(request.getEmail(), request.getLang());
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("");
    }
}
