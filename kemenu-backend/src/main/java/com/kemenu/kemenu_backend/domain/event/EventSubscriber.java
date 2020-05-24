package com.kemenu.kemenu_backend.domain.event;

public interface EventSubscriber<T extends Event> {
    void subscribe(T event);

    Class<T> eventType();

    DomainEventAddress address();
}
