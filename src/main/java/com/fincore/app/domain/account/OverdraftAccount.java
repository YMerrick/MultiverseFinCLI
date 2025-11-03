package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class OverdraftAccount extends Account{
    private final long overdraftLimit;

    public OverdraftAccount(UUID id, String accountHolder, long balanceInMinorUnit, long overdraftLimitInMinorUnits) {
        super(id, accountHolder, balanceInMinorUnit);
        this.overdraftLimit = overdraftLimitInMinorUnits;
    }

    @Override
    public void withdraw(Money amount) {
        long balance = this.getBalance().asMinorUnits();
        if ((overdraftLimit + balance) < amount.asMinorUnits()) throw new InsufficientFundsException("You will exceed your overdraft");
        super.withdraw(amount);
    }
}
