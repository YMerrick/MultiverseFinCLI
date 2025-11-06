package com.fincore.app.application.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class AuthContext {
    @Getter
    @Setter
    private UUID session = null;
}
