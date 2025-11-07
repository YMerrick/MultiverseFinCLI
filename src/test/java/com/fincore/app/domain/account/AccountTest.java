package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.Money;
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
    public void testWithdraw() {
        Account stubAccount = new Account("Test",2.5);
        long previousState = stubAccount.getBalance().asMinorUnits();

        stubAccount.withdraw(Money.ofMajor(BigDecimal.valueOf(2.0), "GBP"));
        long result = stubAccount.getBalance().asMinorUnits();

        assertNotEquals(previousState, result);
        assertEquals(50, result);
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

    @Test
    void testDepositWithNegative() {
        UUID stubId = UUID.randomUUID();
        Account testAccount = new Account(stubId, "Test", 20);
        assertThrows(
                IllegalStateException.class,
                () -> testAccount.deposit(Money.ofMinor(-200, testAccount.getBalance().getCurrency()))
        );
    }

    @Test
    void testWithdrawWithNegative() {
        UUID stubId = UUID.randomUUID();
        Account testAccount = new Account(stubId, "Test", 20);
        assertThrows(
                IllegalStateException.class,
                () -> testAccount.withdraw(Money.ofMinor(-200, testAccount.getBalance().getCurrency()))
        );
    }
}
