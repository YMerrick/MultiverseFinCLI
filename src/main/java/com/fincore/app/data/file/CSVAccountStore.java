package com.fincore.app.data.file;

import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.account.AccountStore;

import java.util.Optional;
import java.util.UUID;

public class CSVAccountStore implements AccountStore {
    @Override
    public void save(Account account) {

    }

    @Override
    public Optional<Account> getById(UUID id) {
        return Optional.empty();
    }
}
