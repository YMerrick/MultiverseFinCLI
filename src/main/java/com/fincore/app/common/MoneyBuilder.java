package com.fincore.app.common;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyBuilder {
    private long amount = 0;
    private Currency currency = Currency.getInstance("GBP");

    public MoneyBuilder setAmount(long amount) {
        if (amount < 0) throwNegativeException();
        this.amount = amount;
        return this;
    }

    public MoneyBuilder setAmount(BigDecimal amount) {
        if (amount.signum() < 0) throwNegativeException();
        this.amount = amount.scaleByPowerOfTen(2).longValue();
        return this;
    }

    public MoneyBuilder setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public MoneyBuilder setCurrency(String currencyCode) {
        this.currency = Currency.getInstance("GBP");
        return this;
    }

    private void throwNegativeException() throws IllegalArgumentException {
        throw new IllegalArgumentException("Value can not be negative");
    }

    public Money createMoney() {
        return new Money(amount, currency);
    }
}