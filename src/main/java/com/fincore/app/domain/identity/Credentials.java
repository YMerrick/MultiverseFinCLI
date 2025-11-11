package com.fincore.app.domain.identity;

import java.util.UUID;

public record Credentials(String username, String passwordHash, UUID userId) {
}
