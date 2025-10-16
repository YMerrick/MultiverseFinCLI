package com.fincore.app.user;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final AccountId id;
    private final Money balance;
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
        this.accountHolder = accountHolder;
        this.balance = new MoneyBuilder().setAmount(BigDecimal.valueOf(balance)).createMoney();
        this.id = new AccountId(id);
    }

    public Account(String accountHolder, double balance) {
        this(UUID.randomUUID(), accountHolder, balance);
    }

    private void throwNegativeException() throws IllegalArgumentException {
        throw new IllegalArgumentException("Amount can not be negative");
    }

    public void deposit(double amountInCurrencyUnit) {
        if (amountInCurrencyUnit < 0) throwNegativeException();
        long originalAmount = balance.getAmount();
        long depositingAmount = (long) (amountInCurrencyUnit * 100);
        balance.setAmount(depositingAmount + originalAmount);
    }

    public void deposit(long amountInMinorUnits) {
        if (amountInMinorUnits < 0) throwNegativeException();
        balance.setAmount(balance.getAmount() + amountInMinorUnits);
    }

    public void deposit(BigDecimal amountInCurrencyUnit) {
        if (amountInCurrencyUnit.signum() < 0) throwNegativeException();
        long depositingAmount = amountInCurrencyUnit.scaleByPowerOfTen(2).longValue();
        balance.setAmount(depositingAmount + balance.getAmount());
    }

    public void withdraw(double amountInCurrencyUnit) {
        if (amountInCurrencyUnit < 0) throwNegativeException();
        long originalAmount = balance.getAmount();
        long withdrawingAmount = (long) (amountInCurrencyUnit * 100);
        if (withdrawingAmount > originalAmount) throw new IllegalArgumentException("Value can not be more than current balance");
        balance.setAmount(originalAmount - withdrawingAmount);
    }

    public void withdraw(long amountInMinorUnits) {
        if (amountInMinorUnits < 0) throwNegativeException();
        long originalAmount = balance.getAmount();
        if (amountInMinorUnits > originalAmount) throw new IllegalArgumentException("Value can not be more than current balance");
        balance.setAmount(originalAmount - amountInMinorUnits);
    }

    public void withdraw(BigDecimal amountInCurrencyUnit) {
        if (amountInCurrencyUnit.signum() < 0) throwNegativeException();
        long withdrawingAmount = amountInCurrencyUnit.scaleByPowerOfTen(2).longValue();
        long originalAmount = balance.getAmount();
        if (withdrawingAmount > originalAmount) throw new IllegalArgumentException("Value can not be more than current balance");
        balance.setAmount(originalAmount - withdrawingAmount);
    }

    public String getBalance() {
        return balance.get();
    }

    public String toString() {
        return String.format("""
                Account Holder: %s
                Balance: %s
                UUID: %s""",
                accountHolder, getBalance(), id.idToString());
    }
}
