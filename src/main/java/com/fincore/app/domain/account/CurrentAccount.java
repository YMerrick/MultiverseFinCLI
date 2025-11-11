package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class CurrentAccount extends Account{

    public CurrentAccount(UUID id, UUID userId, String accountHolder, Money balance) {
        super(id, userId, accountHolder, balance);
    }

    @Override
    public void withdraw(Money amount) {
        boolean isOverLimit = this.getBalance().minus(amount).asMinorUnits() < 0;
        if (isOverLimit) throw new InsufficientFundsException("Insufficient Funds");
        super.withdraw(amount);
    }
}
