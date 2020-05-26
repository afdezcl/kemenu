package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.email.EmailEventFactory;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    @Value("${app.cors}")
    private List<String> allowedOrigins;

    private final CustomerService customerService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final EventPublisher eventPublisher;
    private final EmailEventFactory emailEventFactory;

    public String sendForgotPasswordEmail(String email, String lang) {
        return customerService.read(email)
                .map(customer -> {
                    String forgotPasswordId = forgotPasswordRepository.save(new ForgotPassword(customer.getEmail()));
                    String forgotUrl = allowedOrigins.get(0) + "/public/forgot/password/" + forgotPasswordId;
                    SendEmailEvent forgotPasswordEmailEvent = emailEventFactory.forgotPasswordEmailEvent(lang, email);
                    SendEmailEvent emailEvent = forgotPasswordEmailEvent.toBuilder()
                            .content(forgotPasswordEmailEvent.getContent() + " " + forgotUrl)
                            .build();
                    eventPublisher.publish(emailEvent);
                    return forgotPasswordId;
                })
                .orElse(UUID.randomUUID().toString());
    }

    public Optional<String> changePassword(String forgotPasswordId, String customerEmail, String newPassword) {
        return forgotPasswordRepository.findById(forgotPasswordId)
                .flatMap(forgotPassword -> {
                    if (customerEmail.equalsIgnoreCase(forgotPassword.getEmail())) {
                        if (!forgotPassword.isUsed() && !forgotPassword.isExpired()) {
                            forgotPassword.use();
                            forgotPasswordRepository.save(forgotPassword);
                            return customerService.changePassword(customerEmail, newPassword);
                        } else {
                            if (forgotPassword.isExpired()) {
                                log.info("A customer with email {} is trying to change password with an expired forgot password request", customerEmail);
                            } else {
                                log.info("A customer with email {} is trying to change password with an used forgot password request", customerEmail);
                            }
                            return Optional.empty();
                        }
                    } else {
                        log.info("A customer with email {} is trying to change password to another email {}", customerEmail, forgotPassword.getEmail());
                        return Optional.empty();
                    }
                });
    }
}
