package com.kemenu.kemenu_backend.domain.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DomainEventAddress {
    EMAIL_CONFIRMATION("email.confirmation"),
    EMAIL_SEND("email.send");

    private final String address;

    @Override
    public String toString() {
        return address;
    }
}
