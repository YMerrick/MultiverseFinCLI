package com.fincore.app.user;

import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private Map<UUID, Session> sessionStore;
    // Credential repository
    // Account repository

    public UUID login(String username, String password) {
        UUID sessionId = UUID.randomUUID();
        // Authenticate



        // Retrieve account from somewhere
        Account user = new Account(UUID.fromString("1234"), "Test", 0);
        AccountId userId = user.getId();
        // Create Session
        // After authentication get user id
        Session newSession = new Session(userId, sessionId);
        // Store Session in session store
        sessionStore.putIfAbsent(sessionId, newSession);
        // Return session ID
        return sessionId;
    }

    // Removes session from session store
    public void logout(UUID sessionId) {
        sessionStore.remove(sessionId);
    }

    /**
     * Used to validate and return current session
     * Otherwise returns null
     * @param sessionId
     * UUID of session to get from store
     * @return Session
     */
    public Session validate(UUID sessionId) {
        return sessionStore.getOrDefault(sessionId, null);
    }

}
