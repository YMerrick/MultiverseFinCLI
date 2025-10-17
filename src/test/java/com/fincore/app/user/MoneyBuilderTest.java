package com.fincore.app.user;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyBuilderTest {
    @Test
    public void testBuild() {
        assertInstanceOf(Money.class, new MoneyBuilder().createMoney(), "Type Money expected");
    }

    @Test
    public void testDefaultBuildValues() {
        Money result = new MoneyBuilder().createMoney();
        String expected = "£0.00";

        assertEquals(0, result.getAmount());
        assertEquals(expected, result.get());
    }

    @Test
    public void testNegativeAmountBuild() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new MoneyBuilder().setAmount(-1);
                },
                "Illegal Argument Exception expected"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    BigDecimal stub = BigDecimal.valueOf(-1.1);
                    new MoneyBuilder().setAmount(stub);
                },
                "Illegal Argument Exception expected"
        );
    }


}