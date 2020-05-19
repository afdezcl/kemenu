package com.kemenu.kemenu_backend.infrastructure.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.domain.event.Event;
import com.kemenu.kemenu_backend.domain.event.EventSubscriber;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class VertxEventSubscriber<T extends Event> extends AbstractVerticle implements EventSubscriber<T> {

    protected final EventBus eventBus;
    protected final ObjectMapper mapper;

    @Override
    public void start() throws Exception {
        eventBus.consumer(address(), (Message<String> message) -> {
            try {
                T event = mapper.readValue(message.body(), eventType());
                subscribe(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
