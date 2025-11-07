package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class OverdraftAccount extends Account{
    private final Money overdraftLimit;

    public OverdraftAccount(UUID id, String accountHolder, Money balance, Money overdraftLimit) {
        super(id, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public OverdraftAccount(UUID id, String accountHolder, long balanceInMinorUnit, long overdraftLimitInMinorUnits) {
        super(id, accountHolder, balanceInMinorUnit);
        this.overdraftLimit = Money.ofMinor(overdraftLimitInMinorUnits, super.getBalance().getCurrency());
    }

    @Override
    public void withdraw(Money amount) {
        boolean isOverLimit = overdraftLimit.plus(this.getBalance()).minus(amount).asMinorUnits() < 0;
        if (isOverLimit) throw new InsufficientFundsException("You will exceed your overdraft");
        super.withdraw(amount);
    }
}
