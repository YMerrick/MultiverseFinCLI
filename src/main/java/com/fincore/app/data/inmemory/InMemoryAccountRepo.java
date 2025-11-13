package com.fincore.app.data.inmemory;

import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.account.AccountRepo;
import com.fincore.app.domain.shared.DuplicateEntityException;

import java.util.*;

public class InMemoryAccountRepo implements AccountRepo {
    private final Map<UUID, Account> store = new HashMap<>();
    private final Map<UUID, List<Account>> storeByUser = new HashMap<>();

    public Optional<Account> getById(UUID id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id can not be null");
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Account> getAccounts(UUID userId) {
        return storeByUser.get(userId);
    }

    public Optional<Account> getById(String id) {
        UUID convertToUuid = UUID.fromString(id);
        return getById(convertToUuid);
    }

    public void save(Account account) {
        if (Objects.isNull(account)) throw new IllegalArgumentException("Account can not be null");
        if (store.containsValue(account)) throw new DuplicateEntityException("Account already exists");
        store.put(account.getId(), account);
        storeByUser.putIfAbsent(account.getUserId(), new ArrayList<>());
        storeByUser.get(account.getUserId()).add(account);
    }
}
