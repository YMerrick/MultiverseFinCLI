package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OverdraftAccountTest {
    private final String EXCEPTION_EXPECTED = "Illegal argument exception expected";
    private UUID stubId;

    @BeforeEach
    void setUp() {
        stubId = UUID.randomUUID();
    }

    @Test
    public void testOverWithdrawal() {
        Account stubAccount = new OverdraftAccount(stubId, "Test", 0, 2000);
        assertThrows(
                InsufficientFundsException.class,
                () -> {
                    stubAccount.withdraw(Money.ofMinor(2001, "GBP"));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testDefaultConstructor() {
        String stubHolder = "Test";
        Money stubBalance = mock(Money.class);
        Money stubLimit = mock(Money.class);

        Account testAccount = new OverdraftAccount(stubId, stubHolder, stubBalance, stubLimit);

        assertInstanceOf(
                OverdraftAccount.class,
                testAccount
        );
    }

    @Test
    public void testWithdrawShouldPass() {
        Account testAccount = new OverdraftAccount(stubId, "Test", 2000, 0);
        testAccount.withdraw(Money.ofMinor(1000, testAccount.getBalance().getCurrency()));
        assertEquals(1000, testAccount.getBalance().asMinorUnits());
    }
}
