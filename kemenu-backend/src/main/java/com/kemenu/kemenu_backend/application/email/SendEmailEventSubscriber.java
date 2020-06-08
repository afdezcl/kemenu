package com.kemenu.kemenu_backend.application.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.DomainEventAddress;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import com.kemenu.kemenu_backend.infrastructure.vertx.VertxEventSubscriber;
import io.vertx.core.eventbus.EventBus;

public class SendEmailEventSubscriber extends VertxEventSubscriber<SendEmailEvent> {

    private final EmailService emailService;

    public SendEmailEventSubscriber(EventBus eventBus, ObjectMapper mapper, EmailService emailService) {
        super(eventBus, mapper);
        this.emailService = emailService;
    }

    @Override
    public void subscribe(SendEmailEvent event) {
        emailService.sendMail(event, event.getType(), event.getContent());
    }

    @Override
    public Class<SendEmailEvent> eventType() {
        return SendEmailEvent.class;
    }

    @Override
    public DomainEventAddress address() {
        return DomainEventAddress.EMAIL_SEND;
    }
}
