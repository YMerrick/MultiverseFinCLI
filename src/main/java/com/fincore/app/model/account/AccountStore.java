package com.fincore.app.model.account;

import java.util.Optional;
import java.util.UUID;

public interface AccountStore {
    void save(Account account);
    Optional<Account> findById(UUID id);
}
