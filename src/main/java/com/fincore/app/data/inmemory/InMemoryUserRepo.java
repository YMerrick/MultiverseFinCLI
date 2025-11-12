package com.fincore.app.data.inmemory;

import com.fincore.app.domain.user.User;
import com.fincore.app.domain.user.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class InMemoryUserRepo implements UserRepo {
    private final Map<UUID, User> storeById;

    public InMemoryUserRepo() {
        this.storeById = new HashMap<>();
    }

    @Override
    public Optional<User> getById(UUID userId) {
        return Optional.ofNullable(storeById.get(userId));
    }

    @Override
    public void save(User user) {
        storeById.put(user.userId(), user);
    }
}
