package com.fincore.app.application.accounts;

import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.account.AccountRepo;
import com.fincore.app.domain.account.AccountType;
import com.fincore.app.domain.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private Account stubAccount;
    private static AccountRepo stubAccStore;
    private static AccountService stubService;

    @BeforeAll
    static void setUpClass() {
        stubAccStore = mock(AccountRepo.class);
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
        Money depositingAmount = Money.ofMajor(BigDecimal.valueOf(20.5), "GBP");
        stubService.deposit(stubId, depositingAmount);
        verify(stubAccStore).getById(stubId);
        verify(this.stubAccount).deposit(depositingAmount);
    }

    @Test
    void testWithdraw() {
        UUID stubId = UUID.randomUUID();
        Money withdrawingAmount = Money.ofMajor(BigDecimal.valueOf(5.5), "GBP");
        stubService.withdraw(stubId, withdrawingAmount);
        verify(stubAccStore).getById(stubId);
        verify(stubAccount).withdraw(withdrawingAmount);
    }

    @Test
    void testRegister() {
        String username = "Test";
        UUID stubId = UUID.randomUUID();
        Money amount = Money.ofMinor(0, "GBP");
        stubService.register(username, stubId, amount, AccountType.CURRENT);
        verify(stubAccStore).save(Mockito.any());
    }
}
