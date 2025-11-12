package com.fincore.app.domain.user;

import java.util.UUID;

public record User(UUID userId, String firstName, String lastName) {
    public static User createUser(UUID userId, String firstName, String lastName) {
        return new User(userId, firstName, lastName);
    }
}
