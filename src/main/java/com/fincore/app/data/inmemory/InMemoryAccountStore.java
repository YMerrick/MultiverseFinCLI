package com.fincore.app.data.inmemory;

import com.fincore.app.model.account.Account;
import com.fincore.app.model.account.AccountStore;
import com.fincore.app.model.shared.DuplicateEntityException;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class InMemoryAccountStore implements AccountStore {
    private final HashMap<UUID, Account> store = new HashMap<>();

    public Optional<Account> getById(UUID id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id can not be null");
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Account> getById(String id) {
        UUID convertToUuid = UUID.fromString(id);
        return getById(convertToUuid);
    }

    public void save(Account account) {
        if (Objects.isNull(account)) throw new IllegalArgumentException("Account can not be null");
        if (store.containsValue(account)) throw new DuplicateEntityException("Account already exists");
        store.put(account.getId(), account);
    }
}
