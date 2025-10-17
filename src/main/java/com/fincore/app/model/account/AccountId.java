package com.fincore.app.model.account;

import java.util.UUID;

public record AccountId(UUID idValue) {
    public String idToString() {
        return idValue.toString();
    }
}