package com.kemenu.kemenu_backend.domain.event;

public interface EventPublisher {
    <T extends Event> void publish(T event);
}
