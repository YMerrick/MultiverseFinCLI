package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.MoneyBuilder;
import com.fincore.app.domain.shared.MoneyFormatter;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Account {
    private final UUID id;
    private Money balance;
    String accountHolder;

    public Account(UUID id, String accountHolder, Money balance) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public Account(UUID id, String accountHolder, long balanceInMinorUnit) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = new MoneyBuilder().setAmount(balanceInMinorUnit).createMoney();
    }

    public Account(String accountHolder, long balanceInMinorUnit) {
        this(UUID.randomUUID(), accountHolder, balanceInMinorUnit);
    }

    public Account(String accountHolder) {
        this(UUID.randomUUID(), accountHolder, 0);
    }

    public Account(UUID id, String accountHolder, double balance) {
        this(id, accountHolder, (long) (balance * 100));
    }

    public Account(String accountHolder, double balance) {
        this(UUID.randomUUID(), accountHolder, balance);
    }

    public void deposit(Money amount) {
        balance = balance.plus(amount);
    }

    public void withdraw(Money amount) {
        balance = balance.minus(amount);
    }

    public String toString() {
        return String.format("""
                Account Holder: %s
                Balance: %s
                UUID: %s""",
                accountHolder, MoneyFormatter.format(balance), id.toString());
    }
}
