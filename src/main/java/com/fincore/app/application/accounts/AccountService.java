package com.fincore.app.application.accounts;

import com.fincore.app.domain.account.*;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class AccountService {
    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void deposit(UUID accId, Money amount) {
        Account acc = accountRepo.getById(accId).get();
        acc.deposit(amount);
    }

    public void withdraw(UUID accId, Money amount) {
        Account acc = accountRepo.getById(accId).get();
        acc.withdraw(amount);
    }

    public Money getBalance(UUID accId) {
        return accountRepo.getById(accId).get().getBalance();
    }

    public void register(String accHolder, UUID userId, Money initialBalance, AccountType accountType) {
        Account acc = switch(accountType) {
            case CURRENT -> new CurrentAccount(
                    UUID.randomUUID(),
                    userId,
                    accHolder,
                    initialBalance
            );
            case OVERDRAFT -> new OverdraftAccount(
                    UUID.randomUUID(),
                    userId,
                    accHolder,
                    initialBalance
            );
        };
        accountRepo.save(acc);
    }
}
