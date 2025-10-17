package com.fincore.app.user;

import java.util.UUID;

record AccountId(UUID idValue) {
    public String idToString() {
        return idValue.toString();
    }
}