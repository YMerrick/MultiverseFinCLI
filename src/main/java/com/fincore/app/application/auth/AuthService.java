package com.fincore.app.application.auth;

import com.fincore.app.model.account.AccountId;
import com.fincore.app.model.identity.Session;

import java.util.UUID;

public class AuthService {
    private SessionManager sessionManager;
    public UUID login(String username, String password) {
        // Get credentials from repository with username

        // Verify or authenticate password is correct

        // retrieve user id from credential repo

        AccountId accId = new AccountId(UUID.randomUUID());
        return sessionManager.issue(accId);
    }

    public void logout(UUID sessionId) {
        sessionManager.terminate(sessionId);
    }
}
