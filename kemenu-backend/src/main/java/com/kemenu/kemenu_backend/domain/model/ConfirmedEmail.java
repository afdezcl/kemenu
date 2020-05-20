package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class ConfirmedEmail {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed
    private String email;
    private boolean confirmed;
    private LocalDate expiration;

    public ConfirmedEmail(String email) {
        this(email, LocalDate.now().plusDays(1));
    }

    public ConfirmedEmail(String email, LocalDate expiration) {
        this(UUID.randomUUID().toString(), email, false, expiration);
    }

    public void confirm() {
        if (!isExpired()) {
            this.confirmed = true;
        }
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiration);
    }

    public boolean canReConfirm() {
        return isExpired() && !confirmed;
    }

    public void addOneMoreDayOfExpiration() {
        if (canReConfirm()) {
            expiration = LocalDate.now().plusDays(1);
        }
    }
}
