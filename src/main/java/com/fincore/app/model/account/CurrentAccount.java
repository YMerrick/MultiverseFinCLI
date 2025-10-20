package com.fincore.app.model.account;

import com.fincore.app.model.shared.InsufficientFundsException;
import com.fincore.app.model.shared.Money;

import java.util.UUID;

public class CurrentAccount extends Account{

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
        long balance = this.getBalance().asMinorUnits();
        if (balance < amount.asMinorUnits()) throw new InsufficientFundsException("Insufficient Funds");
        super.withdraw(amount);
    }
}
