package com.kemenu.kemenu_backend.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfirmedEmailTest {

    @Test
    void canConfirmEmail() {
        ConfirmedEmail confirmedEmail = new ConfirmedEmail("test@example.com");

        confirmedEmail.confirm();

        assertTrue(confirmedEmail.isConfirmed());
        assertFalse(confirmedEmail.isExpired());
        assertFalse(confirmedEmail.canReConfirm());
    }

    @Test
    void ifItIsExpiredAndIsNotConfirmedCanReConfirm() {
        ConfirmedEmail confirmedEmail = new ConfirmedEmail("test@example.com", Instant.now().minusSeconds(100));

        assertFalse(confirmedEmail.isConfirmed());
        assertTrue(confirmedEmail.isExpired());
        assertTrue(confirmedEmail.canReConfirm());

        confirmedEmail.addOneMoreDayOfExpiration();

        assertFalse(confirmedEmail.isConfirmed());
        assertFalse(confirmedEmail.isExpired());
        assertFalse(confirmedEmail.canReConfirm());

        confirmedEmail.confirm();

        assertTrue(confirmedEmail.isConfirmed());
        assertFalse(confirmedEmail.isExpired());
        assertFalse(confirmedEmail.canReConfirm());
    }
}