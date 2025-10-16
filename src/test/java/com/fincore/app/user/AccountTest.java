package com.fincore.app.user;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    static final String EXCEPTION_EXPECTED = "Illegal argument exception expected";

    @Test
    public void testDeposit() {
        Account stubAccount = new Account("Test");
        String previousState = stubAccount.getBalance();

        stubAccount.deposit(1);
        String result = stubAccount.getBalance();

        assertNotEquals(previousState, result);
        assertEquals("£0.01", result);
    }

    @Test
    public void testNegativeDeposit() {
        Account stubAccount = new Account("Test");
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stubAccount.deposit(-1);
                },
                EXCEPTION_EXPECTED
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stubAccount.deposit(BigDecimal.valueOf(-1.1));
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testWithdraw() {
        Account stubAccount = new Account("Test",2.5);
        String previousState = stubAccount.getBalance();

        stubAccount.withdraw(2.0);
        String result = stubAccount.getBalance();

        assertNotEquals(previousState, result);
        assertEquals("£0.50", result);
    }

    @Test
    public void testOverWithdrawl() {
        Account stubAccount = new Account("Test");
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stubAccount.withdraw(1);
                },
                EXCEPTION_EXPECTED
        );
    }

    @Test
    public void testNegativeWithdraw() {
        Account stub = new Account("Test");
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stub.withdraw(-1);
                },
                EXCEPTION_EXPECTED
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    stub.withdraw(BigDecimal.valueOf(-1.1));
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
