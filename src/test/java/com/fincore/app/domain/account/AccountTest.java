package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    static final String EXCEPTION_EXPECTED = "Illegal argument exception expected";

    @Test
    public void testDeposit() {
        Account stubAccount = new Account("Test");
        long previousState = stubAccount.getBalance().asMinorUnits();
        Money depositAmount = Money.ofMinor(1, "GBP");

        stubAccount.deposit(depositAmount);
        long result = stubAccount.getBalance().asMinorUnits();

        assertNotEquals(previousState, result);
        assertEquals(1, result);
    }

    @Test
    @Disabled
    public void testNegativeDeposit() {
        Account stubAccount = new Account("Test");
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stubAccount.deposit(Money.ofMinor(-1, "GBP"));
                },
                EXCEPTION_EXPECTED
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stubAccount.deposit(Money.ofMajor(BigDecimal.valueOf(-1.1), "GBP"));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testWithdraw() {
        Account stubAccount = new Account("Test",2.5);
        long previousState = stubAccount.getBalance().asMinorUnits();

        stubAccount.withdraw(Money.ofMajor(BigDecimal.valueOf(2.0), "GBP"));
        long result = stubAccount.getBalance().asMinorUnits();

        assertNotEquals(previousState, result);
        assertEquals(50, result);
    }

    @Test
    @Disabled
    public void testNegativeWithdraw() {
        Account stub = new Account("Test");
        assertThrows(
                RuntimeException.class,
                () -> {
                    stub.withdraw(Money.ofMinor(-1, "GBP"));
                },
                EXCEPTION_EXPECTED
        );

        assertThrows(
                RuntimeException.class,
                () -> {
                    stub.withdraw(Money.ofMajor(BigDecimal.valueOf(-1.1), "GBP"));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testToString() {
        UUID stubId = UUID.randomUUID();
        Account stub = new Account(stubId, "Test", 10);
        String expected = String.format("""
                Account Holder: Test
                Balance: £0.10
                UUID: %s""", stubId);

        assertEquals(expected, stub.toString());
    }
}
