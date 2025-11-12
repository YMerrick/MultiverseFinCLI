package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class OverdraftAccount extends Account{
    private final Money overdraftLimit;

    public OverdraftAccount(UUID id, UUID userId, String accountHolder, Money balance) {
        super(id, userId, accountHolder, balance);
        this.overdraftLimit = Money.ofMinor(2000, balance.getCurrency());
    }

    @Override
    public void withdraw(Money amount) {
        boolean isOverLimit = overdraftLimit.plus(this.getBalance()).minus(amount).asMinorUnits() < 0;
        if (isOverLimit) throw new InsufficientFundsException("You will exceed your overdraft");
        super.withdraw(amount);
    }
}
