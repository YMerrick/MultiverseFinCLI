package com.fincore.app.application.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

public class AuthContext {
    @Getter
    @Setter
    private UUID session = null;

    public boolean isAuthenticated() {
        return Objects.nonNull(session);
    }
}
