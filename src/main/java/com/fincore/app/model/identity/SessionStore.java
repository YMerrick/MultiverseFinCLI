package com.fincore.app.model.identity;

import java.util.Optional;
import java.util.UUID;

public interface SessionStore {
    void save(UUID sessionID, Session sessionData);
    void dismiss(UUID sessionID);
    Optional<Session> getById(UUID sessionID);
}
