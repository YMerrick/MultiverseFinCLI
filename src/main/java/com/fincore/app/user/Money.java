package com.fincore.app.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

final class Money {
    @Getter
    @Setter
    private long amount;
    private final Currency currency;

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String get() {
        String displayAmount = BigDecimal.valueOf(amount,2).toString();
        return currency.getSymbol() + displayAmount;
    }
}