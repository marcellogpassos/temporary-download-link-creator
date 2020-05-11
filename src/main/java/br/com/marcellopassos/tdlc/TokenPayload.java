package br.com.marcellopassos.tdlc;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

public class TokenPayload<R extends Serializable, U extends Serializable> implements Serializable {

    private final Class resourceClass;

    private final R resourceId;

    private U userId;

    private final LocalDateTime createdAt;

    public TokenPayload(Class resourceClass, R resourceId) {
        this.resourceClass = resourceClass;
        this.resourceId = resourceId;
        this.createdAt = LocalDateTime.now();
    }

    public TokenPayload(Class resourceClass, R resourceId, U userId) {
        this(resourceClass, resourceId);
        this.userId = userId;
    }

    public TokenPayload(Identifiable<R> resource) {
        this(resource.getClass(), resource.getId());
    }

    public TokenPayload(Identifiable<R> resource, Identifiable<U> user) {
        this(resource);
        this.userId = user.getId();
    }

    public Class getResourceClass() {
        return resourceClass;
    }

    public R getResourceId() {
        return resourceId;
    }

    public Optional<U> getUserId() {
        return Optional.ofNullable(userId);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isTokenExpired(Long expiration) {
        return LocalDateTime.now().isAfter(this.createdAt.plus(expiration, ChronoUnit.MILLIS));
    }

    public boolean isPermissionGranted(U requestUserId) {
        return Objects.isNull(this.userId) || this.userId.equals(requestUserId);
    }
}
