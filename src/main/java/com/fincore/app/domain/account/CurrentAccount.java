package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;

import java.util.UUID;

public class CurrentAccount extends Account{

    public CurrentAccount(UUID id, String accountHolder, Money balance) {
        super(id, accountHolder, balance);
    }

    public CurrentAccount(UUID id, String accountHolder, long balanceInMinorUnit) {
        super(id, accountHolder, balanceInMinorUnit);
    }

    public CurrentAccount(String accountHolder, long balanceInMinorUnit) {
        super(accountHolder, balanceInMinorUnit);
    }

    public CurrentAccount(String accountHolder) {
        super(accountHolder);
    }

    public CurrentAccount(UUID id, String accountHolder, double balance) {
        super(id, accountHolder, balance);
    }

    public CurrentAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public void withdraw(Money amount) {
        boolean isOverLimit = this.getBalance().minus(amount).asMinorUnits() < 0;
        if (isOverLimit) throw new InsufficientFundsException("Insufficient Funds");
        super.withdraw(amount);
    }
}
