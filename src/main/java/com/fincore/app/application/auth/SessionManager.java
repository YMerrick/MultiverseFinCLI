package com.fincore.app.application.auth;

import com.fincore.app.model.identity.Session;
import com.fincore.app.model.identity.SessionStore;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {
    SessionStore storage;

    public SessionManager(SessionStore seshStore) {
        this.storage = seshStore;
    }

    public UUID issue(UUID accId) {
        if (Objects.isNull(accId)) throw new IllegalArgumentException();
        UUID sessionId = UUID.randomUUID();

        Session newSession = new Session(accId, sessionId);
        // Store Session in session store
        storage.save(sessionId, newSession);
        // Return session ID
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
