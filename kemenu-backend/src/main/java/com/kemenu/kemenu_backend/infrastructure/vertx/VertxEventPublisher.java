package com.kemenu.kemenu_backend.infrastructure.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.Event;
import com.kemenu.kemenu_backend.domain.event.EventPublisher;
import io.vertx.core.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class VertxEventPublisher implements EventPublisher {

    private final EventBus eventBus;
    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public <T extends Event> void publish(T event) {
        eventBus.send(event.address().toString(), mapper.writeValueAsString(event));
    }
}
