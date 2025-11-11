package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.MoneyFormatter;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Account {
    private final UUID id;
    private Money balance;
    private final UUID userId;
    String accountHolder;

    public Account(UUID id, UUID userId, String accountHolder, Money balance) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.userId = userId;
    }

    public void deposit(Money amount) {
        checkNegativeAmount(amount, "deposit");
        balance = balance.plus(amount);
    }

    public void withdraw(Money amount) {
        checkNegativeAmount(amount, "withdraw");
        balance = balance.minus(amount);
    }

    public String toString() {
        return String.format("""
                Account Holder: %s
                Balance: %s
                UUID: %s""",
                accountHolder, MoneyFormatter.format(balance), id.toString());
    }

    private void checkNegativeAmount(Money amount, String transaction) {
        if (amount.asMinorUnits() < 0)
            throw new IllegalStateException(
                    String.format("Can not %s a negative amount", transaction)
            );
    }
}
