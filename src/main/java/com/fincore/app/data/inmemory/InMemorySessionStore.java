package com.fincore.app.data.inmemory;

import com.fincore.app.model.identity.Session;
import com.fincore.app.model.identity.SessionStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemorySessionStore implements SessionStore {
    private final Map<UUID, Session> cache = new HashMap<UUID, Session>();
    @Override
    public void save(UUID sessionID, Session sessionData) {
        cache.put(sessionID, sessionData);
    }

    @Override
    public void dismiss(UUID sessionID) {
        cache.remove(sessionID);
    }

    @Override
    public Optional<Session> getById(UUID sessionID) {
        return Optional.ofNullable(cache.getOrDefault(sessionID, null));
    }
}
