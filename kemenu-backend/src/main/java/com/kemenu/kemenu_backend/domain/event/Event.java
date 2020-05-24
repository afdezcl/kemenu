package com.kemenu.kemenu_backend.domain.event;

public interface Event {
    String id();

    DomainEventAddress address();
}
