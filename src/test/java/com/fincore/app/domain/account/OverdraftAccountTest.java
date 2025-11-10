package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OverdraftAccountTest {
    private final String EXCEPTION_EXPECTED = "Illegal argument exception expected";
    @Test
    public void testOverWithdrawal() {
        Account stubAccount = new OverdraftAccount(UUID.randomUUID(), "Test", 0, 2000);
        stubAccount.withdraw(Money.ofMinor(2000, "GBP"));
        assertThrows(
                InsufficientFundsException.class,
                () -> {
                    stubAccount.withdraw(Money.ofMinor(1, "GBP"));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testConstructorWithDefault() {
        UUID stubId = UUID.randomUUID();
        String stubHolder = "Test";
        Money stubBalance = mock(Money.class);
        Money stubLimit = mock(Money.class);

        Account testAccount = new OverdraftAccount(stubId, stubHolder, stubBalance, stubLimit);

        assertInstanceOf(
                OverdraftAccount.class,
                testAccount
        );
    }
}
