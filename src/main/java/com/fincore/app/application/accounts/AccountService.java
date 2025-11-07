package com.fincore.app.application.accounts;

import com.fincore.app.domain.account.*;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class AccountService {
    private AccountStore accountStorage;

    public AccountService(AccountStore accountStorage) {
        this.accountStorage = accountStorage;
    }

    public void deposit(UUID accId, Money amount) {
        Account acc = accountStorage.getById(accId).get();
        acc.deposit(amount);
    }

    public void withdraw(UUID accId, Money amount) {
        Account acc = accountStorage.getById(accId).get();
        acc.withdraw(amount);
    }

    public Money getBalance(UUID accId) {
        return accountStorage.getById(accId).get().getBalance();
    }

    public void register(String accHolder, UUID accId, Money initialBalance, AccountType accountType) {
        Account acc = switch(accountType) {
            case CURRENT -> new CurrentAccount(
                    accId,
                    accHolder,
                    initialBalance
            );
            case OVERDRAFT -> new OverdraftAccount(
                    accId,
                    accHolder,
                    initialBalance,
                    Money.ofMinor(2000, initialBalance.getCurrency())
            );
        };
        accountStorage.save(acc);
    }
}
