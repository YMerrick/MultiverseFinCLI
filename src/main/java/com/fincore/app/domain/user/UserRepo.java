package com.fincore.app.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo {
    Optional<User> getById(UUID userId);
    Optional<User> getByFullName(String firstName, String lastName);
    void save(User user);
}
