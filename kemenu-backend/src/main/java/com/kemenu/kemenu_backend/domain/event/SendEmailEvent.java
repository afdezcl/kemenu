package com.kemenu.kemenu_backend.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = SendEmailEvent.SendEmailEventBuilder.class)
public class SendEmailEvent implements Event {

    private static final String NO_REPLAY_EMAIL = "noreply@kemenu.com";

    @Builder.Default
    String id = UUID.randomUUID().toString();
    String from;
    String to;
    String subject;
    String content;

    public static SendEmailEvent noReplyEmail(String to, String subject, String content) {
        return SendEmailEvent.builder()
                .from(NO_REPLAY_EMAIL)
                .to(to)
                .subject(subject)
                .content(content)
                .build();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public DomainEventAddress address() {
        return DomainEventAddress.EMAIL_SEND;
    }
}
