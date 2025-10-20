package com.fincore.app.model.account;

import com.fincore.app.model.shared.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentAccountTest {
    private final String EXCEPTION_EXPECTED = "Illegal argument exception expected";
    @Test
    public void testOverWithdrawal() {
        Account stubAccount = new CurrentAccount("Test");
        assertThrows(
                InsufficientFundsException.class,
                () -> {
                    stubAccount.withdraw(1);
                },
                EXCEPTION_EXPECTED
        );
    }
}
