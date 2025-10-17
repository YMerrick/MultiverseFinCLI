package com.fincore.app.model.shared;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

public final class Money {
    private long minorUnits;
    @Getter
    private final Currency currency;

    public Money(long amount, Currency currency) {
        this.minorUnits = amount;
        this.currency = currency;
    }

    public long asMinorUnits() {
        return minorUnits;
    }

    public BigDecimal asMajorUnits() {
        return BigDecimal.valueOf(minorUnits, 2);
    }

    public Money plus(Money other) {
        if (!currency.equals(other.currency)) throw new RuntimeException("Currencies are not the same");
        return ofMinor(Math.addExact(minorUnits, other.minorUnits), currency);
    }

    public Money minus(Money other) {
        if (!currency.equals(other.currency)) throw new RuntimeException("Currencies are not the same");
        return ofMinor(Math.subtractExact(minorUnits, other.minorUnits), currency);
    }

    public static Money ofMinor(long minor, Currency cur) {
        return new Money(minor, cur);
    }

    public static Money ofMinor(long minor, String cur) {
        return ofMinor(minor, Currency.getInstance(cur));
    }

    public static Money ofMajor(BigDecimal major, Currency cur) {
        return new Money(major.scaleByPowerOfTen(2).longValue(), cur);
    }

    public static Money ofMajor(BigDecimal major, String cur) {
        return ofMajor(major, Currency.getInstance(cur));
    }
}