package com.fincore.app.application.auth;

import com.fincore.app.domain.identity.Session;
import com.fincore.app.domain.identity.SessionStore;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {
    SessionStore storage;

    public SessionManager(SessionStore seshStore) {
        this.storage = seshStore;
    }

    public UUID issue(UUID userId) {
        if (Objects.isNull(userId)) throw new IllegalArgumentException();
        UUID sessionId = UUID.randomUUID();

        Session newSession = new Session(null, userId, sessionId);
        storage.save(sessionId, newSession);
        return newSession.id();
    }

    // Removes session from session store
    public void terminate(UUID sessionId) {
        storage.dismiss(sessionId);
    }

    public Optional<Session> validate(UUID sessionId) {
        return storage.getById(sessionId);
    }

}
