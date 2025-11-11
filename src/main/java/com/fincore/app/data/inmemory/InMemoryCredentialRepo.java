package com.fincore.app.data.inmemory;

import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;
import com.fincore.app.domain.shared.DuplicateEntityException;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCredentialRepo implements CredentialRepo {
    private final HashMap<String, Credentials> store = new HashMap<>();

    @Override
    public Optional<Credentials> getByUsername(String username) {
        if (username.isBlank()) throw new IllegalArgumentException("Username can not be empty");
        return Optional.ofNullable(store.get(username));
    }

    @Override
    public void save(Credentials cred) {
        if (Objects.isNull(cred)) throw new IllegalArgumentException("Can not pass a null");
        if (store.containsValue(cred)) throw new DuplicateEntityException("That user already exists");
        store.put(cred.username(), cred);
    }
}
