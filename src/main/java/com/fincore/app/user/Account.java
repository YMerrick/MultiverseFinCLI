package com.fincore.app.user;

import com.fincore.app.common.Money;
import com.fincore.app.common.MoneyBuilder;
import com.fincore.app.common.MoneyFormatter;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    @Getter
    private final AccountId id;
    @Getter
    private Money balance;
    @Getter
    String accountHolder;

    public Account(UUID id, String accountHolder, long balanceInMinorUnit) {
        this.id = new AccountId(id);
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

    private void throwNegativeException() throws IllegalArgumentException {
        throw new IllegalArgumentException("Amount can not be negative");
    }

    public void deposit(Money amount) {
        balance = balance.plus(amount);
    }

    public void deposit(double amountInCurrencyUnit) {
        if (amountInCurrencyUnit < 0) throwNegativeException();
        deposit(Money.ofMajor(BigDecimal.valueOf(amountInCurrencyUnit), balance.getCurrency()));
    }

    public void deposit(long amountInMinorUnits) {
        if (amountInMinorUnits < 0) throwNegativeException();
        deposit(Money.ofMinor(amountInMinorUnits, balance.getCurrency()));
    }

    public void deposit(BigDecimal amountInCurrencyUnit) {
        if (amountInCurrencyUnit.signum() < 0) throwNegativeException();
        deposit(Money.ofMajor(amountInCurrencyUnit, balance.getCurrency()));
    }

    public void withdraw(Money amount) {
        balance = balance.minus(amount);
    }

    public void withdraw(double amountInCurrencyUnit) {
        if (amountInCurrencyUnit < 0) throwNegativeException();
        withdraw(Money.ofMajor(BigDecimal.valueOf(amountInCurrencyUnit), balance.getCurrency()));
    }

    public void withdraw(long amountInMinorUnits) {
        if (amountInMinorUnits < 0) throwNegativeException();
        withdraw(Money.ofMinor(amountInMinorUnits, balance.getCurrency()));
    }

    public void withdraw(BigDecimal amountInCurrencyUnit) {
        if (amountInCurrencyUnit.signum() < 0) throwNegativeException();
        withdraw(Money.ofMajor(amountInCurrencyUnit, balance.getCurrency()));
    }

    public String toString() {
        return String.format("""
                Account Holder: %s
                Balance: %s
                UUID: %s""",
                accountHolder, MoneyFormatter.format(balance), id.idToString());
    }
}
