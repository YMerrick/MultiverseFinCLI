package com.fincore.app.model.identity;

import java.util.Optional;
import java.util.UUID;

public interface SessionStore {
    public void save(UUID sessionID, Session sessionData);
    public void dismiss(UUID sessionID);
    public Optional<Session> getById(UUID sessionID);
}
