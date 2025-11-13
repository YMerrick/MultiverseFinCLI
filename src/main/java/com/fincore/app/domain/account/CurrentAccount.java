package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.MoneyFormatter;

import java.util.UUID;

public class CurrentAccount extends Account{
    private final AccountType type;

    public CurrentAccount(UUID id, UUID userId, String accountHolder, Money balance) {
        super(id, userId, accountHolder, balance);
        this.type = AccountType.CURRENT;
    }

    @Override
    public void withdraw(Money amount) {
        boolean isOverLimit = this.getBalance().minus(amount).asMinorUnits() < 0;
        if (isOverLimit) throw new InsufficientFundsException("Insufficient Funds");
        super.withdraw(amount);
    }

    @Override
    public String repr() {
        return String.format("""
                Type: %s
                Balance: %s
                ID: %s""",
                type, MoneyFormatter.format(this.getBalance()), this.getId()
        );
    }
}
