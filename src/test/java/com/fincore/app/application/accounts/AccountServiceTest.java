package com.fincore.app.application.accounts;

import com.fincore.app.model.account.Account;
import com.fincore.app.model.account.AccountStore;
import com.fincore.app.model.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private Account stubAccount;
    private static AccountStore stubAccStore;
    private static AccountService stubService;

    @BeforeAll
    static void setUpClass() {
        stubAccStore = mock(AccountStore.class);
        stubService = new AccountService(stubAccStore);
    }

    @BeforeEach
    void setUp() {
        this.stubAccount = mock(Account.class);
        when(stubAccStore.getById(Mockito.any())).thenReturn(Optional.ofNullable(this.stubAccount));
    }

    @Test
    void testGetBalance() {
        UUID stubId = UUID.randomUUID();
        Money result = stubService.getBalance(stubId);
        verify(stubAccStore).getById(stubId);
        verify(stubAccount).getBalance();
    }

    @Test
    void testDeposit() {
        UUID stubId = UUID.randomUUID();
        double depositingAmount = 20.5;
        stubService.deposit(stubId, depositingAmount);
        verify(stubAccStore).getById(stubId);
        verify(this.stubAccount).deposit(depositingAmount);
    }

    @Test
    void testWithdraw() {
        UUID stubId = UUID.randomUUID();
        double withdrawingAmount = 5.5;
        stubService.withdraw(stubId, withdrawingAmount);
        verify(stubAccStore).getById(stubId);
        verify(stubAccount).withdraw(withdrawingAmount);
    }
}
