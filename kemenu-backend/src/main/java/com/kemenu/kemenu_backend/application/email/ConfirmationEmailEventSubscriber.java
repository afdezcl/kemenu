package com.kemenu.kemenu_backend.application.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.ConfirmationEmailEvent;
import com.kemenu.kemenu_backend.domain.event.DomainEventAddress;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.infrastructure.vertx.VertxEventSubscriber;
import io.vertx.core.eventbus.EventBus;

public class ConfirmationEmailEventSubscriber extends VertxEventSubscriber<ConfirmationEmailEvent> {

    private final ConfirmedEmailRepository confirmedEmailRepository;
    private final String kemenuDomain;
    private final EventPublisher eventPublisher;

    public ConfirmationEmailEventSubscriber(EventBus eventBus,
                                            ObjectMapper mapper,
                                            ConfirmedEmailRepository confirmedEmailRepository,
                                            String kemenuDomain,
                                            EventPublisher eventPublisher) {
        super(eventBus, mapper);
        this.confirmedEmailRepository = confirmedEmailRepository;
        this.kemenuDomain = kemenuDomain;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void subscribe(ConfirmationEmailEvent event) {
        String confirmEmailId = confirmedEmailRepository.save(new ConfirmedEmail(event.getSendEmailEvent().getTo()));
        String confirmUrl = kemenuDomain + "/public/confirm/email/" + confirmEmailId;
        SendEmailEvent sendEmailEvent = event.getSendEmailEvent().toBuilder()
                .content(event.getSendEmailEvent().getContent() + " " + confirmUrl)
                .build();
        eventPublisher.publish(sendEmailEvent);
    }

    @Override
    public Class<ConfirmationEmailEvent> eventType() {
        return ConfirmationEmailEvent.class;
    }

    @Override
    public DomainEventAddress address() {
        return DomainEventAddress.EMAIL_CONFIRMATION;
    }
}
