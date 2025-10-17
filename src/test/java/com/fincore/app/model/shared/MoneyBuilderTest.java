package com.fincore.app.model.shared;

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

        assertEquals(0, result.asMinorUnits());
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