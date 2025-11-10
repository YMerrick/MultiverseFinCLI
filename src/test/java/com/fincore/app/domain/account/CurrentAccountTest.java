package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CurrentAccountTest {
    private final String EXCEPTION_EXPECTED = "Illegal argument exception expected";
    @Test
    public void testOverWithdrawal() {
        Account stubAccount = new CurrentAccount("Test");
        assertThrows(
                InsufficientFundsException.class,
                () -> {
                    stubAccount.withdraw(Money.ofMinor(1, "GBP"));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testConstructor() {
        Money stubBalance = mock(Money.class);
        String stubHolder = "Test";
        UUID stubId = UUID.randomUUID();

        Account result = new CurrentAccount(stubId, stubHolder, stubBalance);
        assertInstanceOf(CurrentAccount.class, result);
    }
}
