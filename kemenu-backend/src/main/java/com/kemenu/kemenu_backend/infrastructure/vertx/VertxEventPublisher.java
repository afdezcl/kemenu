package com.kemenu.kemenu_backend.infrastructure.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.Event;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import io.vertx.core.eventbus.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class VertxEventPublisher implements EventPublisher {

    private final EventBus eventBus;
    private final ObjectMapper mapper;

    @Override
    public <T extends Event> void publish(T event) {
        try {
            eventBus.send(event.address().toString(), mapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
