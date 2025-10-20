package com.fincore.app.model.account;

import com.fincore.app.model.shared.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OverdraftAccountTest {
    private final String EXCEPTION_EXPECTED = "Illegal argument exception expected";
    @Test
    public void testOverWithdrawal() {
        Account stubAccount = new OverdraftAccount(UUID.randomUUID(), "Test", 0, 2000);
        stubAccount.withdraw(2000);
        assertThrows(
                InsufficientFundsException.class,
                () -> {
                    stubAccount.withdraw(1);
                },
                EXCEPTION_EXPECTED
        );
    }
}
