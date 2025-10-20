package com.fincore.app.model.identity;

import java.util.UUID;

public record Credentials(String username, String passwordHash, UUID accId) {
}
