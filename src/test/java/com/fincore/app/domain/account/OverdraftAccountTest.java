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
    private UUID stubUserId;

    @BeforeEach
    void setUp() {
        stubId = UUID.randomUUID();
        stubUserId = UUID.randomUUID();
    }

    @Test
    public void testOverWithdrawal() {
        Money initialBalance = Money.ofMinor(0, "GBP");
        Account stubAccount = new OverdraftAccount(
                stubId,
                stubUserId,
                "Test",
                initialBalance
        );
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

        Account testAccount = new OverdraftAccount(
                stubId,
                stubUserId,
                stubHolder,
                stubBalance
        );

        assertInstanceOf(
                OverdraftAccount.class,
                testAccount
        );
    }

    @Test
    public void testWithdrawShouldPass() {
        Money initialBalance = Money.ofMinor(2000, "GBP");
        Account testAccount = new OverdraftAccount(
                stubId,
                stubUserId,
                "Test",
                initialBalance
        );
        testAccount.withdraw(Money.ofMinor(1000, testAccount.getBalance().getCurrency()));
        assertEquals(1000, testAccount.getBalance().asMinorUnits());
    }
}
