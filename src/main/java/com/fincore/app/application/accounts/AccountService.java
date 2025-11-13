package com.fincore.app.application.accounts;

import com.fincore.app.domain.account.*;
import com.fincore.app.domain.shared.AccountException;
import com.fincore.app.domain.shared.Money;

import java.util.List;
import java.util.UUID;

public class AccountService {
    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void deposit(UUID accId, Money amount) {
        Account acc = accountRepo.getById(accId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );
        acc.deposit(amount);
    }

    public void withdraw(UUID accId, Money amount) {
        Account acc = accountRepo.getById(accId).orElseThrow(
                () -> new AccountException("Account does not exist")
        );
        acc.withdraw(amount);
    }

    public Money getBalance(UUID accId) {
        return accountRepo.getById(accId).get().getBalance();
    }

    public List<Account> getAccounts(UUID userId) {
        List<Account> accountList;

        accountList = accountRepo.getAccounts(userId);
        if (accountList.isEmpty()) throw new AccountException("There are no accounts attached to this user");
        return accountList;
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
