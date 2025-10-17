package com.fincore.app.user;

import java.util.UUID;

public class Session {
    private final UUID sessionId;
    private final AccountId userId;
    // private roles field

    public Session(AccountId user, UUID sessionId) {
        this.sessionId = sessionId;
        this.userId = user;
    }


}
