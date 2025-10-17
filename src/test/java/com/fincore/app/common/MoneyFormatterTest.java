package com.fincore.app.common;

import com.fincore.app.model.shared.Money;
import com.fincore.app.model.shared.MoneyFormatter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MoneyFormatterTest {
    @Test
    void testFormat() {
        Money stub = mock(Money.class);
        when(stub.getCurrency()).thenReturn(Currency.getInstance("GBP"));
        when(stub.asMajorUnits()).thenReturn(BigDecimal.valueOf(2.0));
        assertEquals("£2.00", MoneyFormatter.format(stub));
    }
}
