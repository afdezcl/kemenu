package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import com.kemenu.kemenu_backend.application.email.EmailEventFactory;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    @Value("${app.cors}")
    private List<String> allowedOrigins;

    private final CustomerService customerService;
    private final EventPublisher eventPublisher;
    private final EmailEventFactory emailEventFactory;

    public void sendForgotPasswordEmail(String email, String lang) {
        customerService.read(email)
                .ifPresent(c -> {
                    String forgotUrl = allowedOrigins.get(0) + "/public/forgot/password/" + c.getId();
                    SendEmailEvent forgotPasswordEmailEvent = emailEventFactory.forgotPasswordEmailEvent(lang, email);
                    SendEmailEvent emailEvent = forgotPasswordEmailEvent.toBuilder()
                            .content(forgotPasswordEmailEvent.getContent() + " " + forgotUrl)
                            .build();
                    eventPublisher.publish(emailEvent);
                });
    }
}
