package com.kemenu.kemenu_backend.application.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.ConfirmationEmailEvent;
import com.kemenu.kemenu_backend.domain.event.DomainEventAddress;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import com.kemenu.kemenu_backend.domain.event.SendEmailEvent;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.infrastructure.vertx.VertxEventSubscriber;
import freemarker.template.Template;
import io.vertx.core.eventbus.EventBus;
import lombok.SneakyThrows;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationEmailEventSubscriber extends VertxEventSubscriber<ConfirmationEmailEvent> {

    private final ConfirmedEmailRepository confirmedEmailRepository;
    private final String kemenuDomain;
    private final EventPublisher eventPublisher;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public ConfirmationEmailEventSubscriber(EventBus eventBus,
                                            ObjectMapper mapper,
                                            ConfirmedEmailRepository confirmedEmailRepository,
                                            String kemenuDomain,
                                            EventPublisher eventPublisher,
                                            FreeMarkerConfigurer freeMarkerConfigurer) {
        super(eventBus, mapper);
        this.confirmedEmailRepository = confirmedEmailRepository;
        this.kemenuDomain = kemenuDomain;
        this.eventPublisher = eventPublisher;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    @SneakyThrows
    public void subscribe(ConfirmationEmailEvent event) {
        String confirmEmailId = confirmedEmailRepository.save(new ConfirmedEmail(event.getSendEmailEvent().getTo()));
        String confirmUrl = kemenuDomain + "/public/confirm/email/" + confirmEmailId;
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(event.getHtmlTemplate());
        Map<String, Object> data = new HashMap<>();
        data.put("confirmUrl", confirmUrl);
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        SendEmailEvent sendEmailEvent = event.getSendEmailEvent().toBuilder()
                .content(emailContent)
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
