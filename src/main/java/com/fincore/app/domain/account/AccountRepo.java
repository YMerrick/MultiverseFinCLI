package com.fincore.app.domain.account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepo {
    void save(Account account);
    Optional<Account> getById(UUID id);
}
