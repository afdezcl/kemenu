package com.kemenu.kemenu_backend.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = EmailSendingEvent.EmailSendingEventBuilder.class)
public class EmailSendingEvent implements Event {

    private static final String NO_REPLAY_EMAIL = "noreply@kemenu.com";

    @Builder.Default
    String id = UUID.randomUUID().toString();
    String from;
    String to;
    String subject;
    String content;

    public static EmailSendingEvent emailConfirmation(String to, String subject, String content) {
        return EmailSendingEvent.builder()
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
    public String address() {
        return "email.send";
    }
}
