package com.kemenu.kemenu_backend.application.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.infrastructure.vertx.VertxEventSubscriber;
import io.vertx.core.eventbus.EventBus;

public class EmailEventSubscriber extends VertxEventSubscriber<SendEmailEvent> {

    private final ConfirmedEmailRepository confirmedEmailRepository;
    private final String kemenuDomain;
    private final EmailService emailService;

    public EmailEventSubscriber(EventBus eventBus,
                                ObjectMapper mapper,
                                ConfirmedEmailRepository confirmedEmailRepository,
                                String kemenuDomain,
                                EmailService emailService) {
        super(eventBus, mapper);
        this.confirmedEmailRepository = confirmedEmailRepository;
        this.kemenuDomain = kemenuDomain;
        this.emailService = emailService;
    }

    @Override
    public void subscribe(SendEmailEvent event) {
        String confirmEmailId = confirmedEmailRepository.save(new ConfirmedEmail(event.getTo()));
        String confirmUrl = kemenuDomain + "/confirm/email/" + confirmEmailId;
        emailService.sendMail(event, event.getContent() + " " + confirmUrl);
    }

    @Override
    public Class<SendEmailEvent> eventType() {
        return SendEmailEvent.class;
    }

    @Override
    public String address() {
        return "email.send";
    }
}
