package com.fincore.app.user;

import lombok.Getter;

import java.util.UUID;

@Getter
final class AccountId {
    private final UUID idValue;

    public AccountId() {
        idValue = UUID.randomUUID();
    }
}