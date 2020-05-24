package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class ConfirmedEmail {

    private static final int ONE_DAY_IN_SECONDS = 86400;

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String email;
    private boolean confirmed;
    private Instant expiration;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public ConfirmedEmail(String email) {
        this(email, Instant.now().plusSeconds(ONE_DAY_IN_SECONDS));
    }

    public ConfirmedEmail(String email, Instant expiration) {
        this(UUID.randomUUID().toString(), email, false, expiration, Instant.now(), Instant.now());
    }

    public void confirm() {
        if (!isExpired()) {
            this.confirmed = true;
        }
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiration);
    }

    public boolean canReConfirm() {
        return isExpired() && !confirmed;
    }

    public void addOneMoreDayOfExpiration() {
        if (canReConfirm()) {
            expiration = Instant.now().plusSeconds(ONE_DAY_IN_SECONDS);
        }
    }
}
