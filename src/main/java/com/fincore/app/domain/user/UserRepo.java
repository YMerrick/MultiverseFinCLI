package com.fincore.app.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo {
    Optional<User> getById(UUID userId);
    void save(User user);
}
