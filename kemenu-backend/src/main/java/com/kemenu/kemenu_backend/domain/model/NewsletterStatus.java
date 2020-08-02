package com.kemenu.kemenu_backend.domain.model;

public enum NewsletterStatus {
    OLD, ACCEPTED, REJECTED;

    public boolean isOld() {
        return this == OLD;
    }

    public boolean isAccepted() {
        return this == ACCEPTED;
    }

    public boolean isRejected() {
        return this == REJECTED;
    }
}
