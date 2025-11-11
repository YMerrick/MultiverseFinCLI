package com.fincore.app.domain.identity;

import java.util.UUID;

public record Session(UUID accId, UUID userId, UUID id) {
}
