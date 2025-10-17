package com.fincore.app.model.shared;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void testOfMinor() {
        assertInstanceOf(
                Money.class,
                Money.ofMinor(0, Currency.getInstance("GBP"))
        );
        assertInstanceOf(
                Money.class,
                Money.ofMinor(0, "GBP")
        );
    }

    @Test
    void testOfMajor() {
        assertInstanceOf(
                Money.class,
                Money.ofMajor(BigDecimal.valueOf(0), "GBP")
        );
        assertInstanceOf(
                Money.class,
                Money.ofMajor(BigDecimal.valueOf(0), Currency.getInstance("GBP"))
        );
    }

    @Test
    void testPlus() {
        Money test = new Money(0, Currency.getInstance("GBP"));
        Money stubMoney = Money.ofMinor(2, "GBP");
        assertEquals(2, test.plus(stubMoney).asMinorUnits());
    }

    @Test
    void testPlusWithDifferentCurrency() {
        Money test = Money.ofMinor(0, "GBP");
        Money stubMoney = Money.ofMinor(0, "USD");
        assertThrows(
                RuntimeException.class,
                () -> test.plus(stubMoney)
        );
    }

    @Test
    void testMinusWithDifferentCurrency() {
        Money test = Money.ofMinor(0, "GBP");
        Money stubMoney = Money.ofMinor(0, "USD");
        assertThrows(
                RuntimeException.class,
                () -> test.minus(stubMoney)
        );
    }

    @Test
    void testMinus() {
        Money test = new Money(2, Currency.getInstance("GBP"));
        Money stubMoney = Money.ofMinor(2, "GBP");
        assertEquals(0, test.minus(stubMoney).asMinorUnits());
    }
}