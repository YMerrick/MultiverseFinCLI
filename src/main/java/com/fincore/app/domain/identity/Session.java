package com.fincore.app.domain.identity;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
public class Session {
    @Setter
    private UUID accId;
    private UUID userId;
    private UUID sessionId;

    public UUID id() {
        return sessionId;
    }

    public UUID accId() {
        return accId;
    }

    public UUID userId() {
        return userId;
    }
}
