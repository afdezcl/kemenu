package com.kemenu.kemenu_backend.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ConfirmationEmailEvent.ConfirmationEmailEventBuilder.class)
public class ConfirmationEmailEvent implements Event {

    SendEmailEvent sendEmailEvent;

    @Override
    public String id() {
        return sendEmailEvent.id();
    }

    @Override
    public DomainEventAddress address() {
        return DomainEventAddress.EMAIL_CONFIRMATION;
    }
}
