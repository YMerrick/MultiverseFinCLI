package com.fincore.app.data.inmemory;

import com.fincore.app.model.identity.CredentialStore;
import com.fincore.app.model.identity.Credentials;
import com.fincore.app.model.shared.DuplicateEntityException;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCredentialStore implements CredentialStore {
    private final HashMap<String, Credentials> store = new HashMap<String, Credentials>();

    @Override
    public Optional<Credentials> findByUsername(String username) {
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
