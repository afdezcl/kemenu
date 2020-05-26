package com.kemenu.kemenu_backend.application.forgot_password;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

import static com.kemenu.kemenu_backend.domain.model.ConfirmedEmail.ONE_DAY_IN_SECONDS;

@Getter
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class ForgotPassword {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String email;
    private boolean used;
    private Instant expiration;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public ForgotPassword(String email) {
        this(email, Instant.now().plusSeconds(ONE_DAY_IN_SECONDS));
    }

    public ForgotPassword(String email, Instant expiration) {
        this(UUID.randomUUID().toString(), email, false, expiration, Instant.now(), Instant.now());
    }

    public void use() {
        if (!isExpired()) {
            this.used = true;
        }
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiration);
    }
}
