package com.fincore.app.domain.account;

import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account stubAccount;
    private UUID stubId;
    static final String EXCEPTION_EXPECTED = "Illegal argument exception expected";

    @BeforeEach
    void setUp() {
        stubId = UUID.randomUUID();
        stubAccount = new Account(
                stubId,
                UUID.randomUUID(),
                "Test",
                Money.ofMinor(0, "GBP")
        );
    }

    @Test
    public void testDeposit() {
        long previousState = stubAccount.getBalance().asMinorUnits();
        Money depositAmount = Money.ofMinor(1, "GBP");

        stubAccount.deposit(depositAmount);
        long result = stubAccount.getBalance().asMinorUnits();

        assertNotEquals(previousState, result);
        assertEquals(1, result);
    }

    @Test
    public void testWithdraw() {
        stubAccount.deposit(Money.ofMinor(250, "GBP"));
        long previousState = stubAccount.getBalance().asMinorUnits();
        stubAccount.withdraw(Money.ofMajor(BigDecimal.valueOf(2.0), "GBP"));
        long result = stubAccount.getBalance().asMinorUnits();

        assertNotEquals(previousState, result);
        assertEquals(50, result);
    }

    @Test
    public void testToString() {
        String expected = String.format("""
                Account Holder: Test
                Balance: £0.00
                UUID: %s""", stubId);

        assertEquals(expected, stubAccount.toString());
    }

    @Test
    void testDepositWithNegative() {
        assertThrows(
                IllegalStateException.class,
                () -> stubAccount.deposit(Money.ofMinor(-200, stubAccount.getBalance().getCurrency()))
        );
    }

    @Test
    void testWithdrawWithNegative() {
        assertThrows(
                IllegalStateException.class,
                () -> stubAccount.withdraw(Money.ofMinor(-200, stubAccount.getBalance().getCurrency()))
        );
    }
}
