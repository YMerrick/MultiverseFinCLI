package com.fincore.app.user;

import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testGet() {
        Money stubMoney = new Money(1000, Currency.getInstance("GBP"));
        String expected = "£10.00";
        assertEquals(expected, stubMoney.get());

        stubMoney.setAmount(0);
        expected = "£0.00";
        assertEquals(expected, stubMoney.get());
    }
}