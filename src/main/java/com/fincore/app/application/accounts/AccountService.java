package com.fincore.app.application.accounts;

import com.fincore.app.model.account.Account;
import com.fincore.app.model.account.AccountStore;
import com.fincore.app.model.shared.Money;

import java.util.UUID;

public class AccountService {
    private AccountStore accountStorage;

    public AccountService(AccountStore accountStorage) {
        this.accountStorage = accountStorage;
    }

    void deposit(UUID accId, double amount) {
        Account acc = accountStorage.getById(accId).get();
        acc.deposit(amount);
    }

    void withdraw(UUID accId, double amount) {
        Account acc = accountStorage.getById(accId).get();
        acc.withdraw(amount);
    }

    Money getBalance(UUID accId) {
        return accountStorage.getById(accId).get().getBalance();
    }
}
