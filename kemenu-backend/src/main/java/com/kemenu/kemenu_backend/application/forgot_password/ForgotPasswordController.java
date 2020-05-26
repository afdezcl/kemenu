package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.application.security.Recaptcha;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
class ForgotPasswordController {

    private final Recaptcha recaptcha;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/public/forgot/password")
    ResponseEntity<UUID> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        if (recaptcha.isValid(request.getRecaptchaToken())) {
            String forgotPasswordId = forgotPasswordService.sendForgotPasswordEmail(request.getEmail(), request.getLang());
            return ResponseEntity.ok(UUID.fromString(forgotPasswordId));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
    }

    @PatchMapping("/public/forgot/password/{forgotPasswordId}/email/{customerEmail}")
    ResponseEntity<UUID> changePassword(@PathVariable String forgotPasswordId,
                                        @PathVariable String customerEmail,
                                        @RequestBody @Valid ForgotPasswordChangeRequest request) {
        if (recaptcha.isValid(request.getRecaptchaToken())) {
            return forgotPasswordService.changePassword(forgotPasswordId, customerEmail, request.getPassword())
                    .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
    }
}
