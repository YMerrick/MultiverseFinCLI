package com.fincore.app.domain.account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepo {
    void save(Account account);
    Optional<Account> getById(UUID id);
    List<Account> getAccounts(UUID userId);
}
