package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.menu.actions.*;
import com.fincore.app.model.account.Account;
import com.fincore.app.model.account.AccountStore;
import com.fincore.app.model.identity.Session;
import com.fincore.app.model.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CommandsTest {
    private AuthService stubAuthService;
    private AccountService stubAccService;
    private SessionManager stubSeshManager;
    private AccountStore stubAccStore;
    private IOHandler stubIO;
    private final String stubUsername = "Test";
    private final char[] stubPassword = {'t', 'e', 's', 't'};
    private Context stubCtx;
    private UUID stubId;

    @BeforeEach
    void setUp() {
        stubAuthService = mock(AuthService.class);
        stubAccStore = mock(AccountStore.class);
        stubAccService = spy(new AccountService(stubAccStore));
        stubSeshManager = mock(SessionManager.class);
        Session stubSesh = mock(Session.class);
        stubIO = mock(IOHandler.class);
        stubCtx = mock(Context.class);
        Account stubAcc = mock(Account.class);

        stubId = UUID.randomUUID();
        when(stubAccStore.getById(any(UUID.class))).thenReturn(Optional.of(stubAcc));
        when(stubSeshManager.validate(Mockito.any())).thenReturn(Optional.of(stubSesh));
        when(stubSesh.accId()).thenReturn(stubId);
    }

    @Test
    void testRegisterCommand() {
        Command stubCommand = new RegisterCommand(
                stubAuthService,
                stubAccService,
                stubUsername,
                stubPassword
        );
        doNothing().when(stubAccStore).save(Mockito.any());
        stubCommand.execute(stubCtx);
        verify(stubAccService).register(anyString(), any(), anyLong());
        verify(stubAuthService).register(any(), any(), any());
    }

    @Test
    void testWithdrawCommand() {
        double stubAmount = 5.5;
        Command stubCommand = new WithdrawCommand(
                stubAmount,
                stubAccService,
                stubSeshManager
        );
        stubCommand.execute(stubCtx);
        verify(stubSeshManager).validate(Mockito.any());
        verify(stubAccService).withdraw(stubId, stubAmount);
    }

    @Test
    void testLogoutCommand() {
        Command stubCommand = new LogoutCommand(
                stubSeshManager
        );
        stubCommand.execute(stubCtx);
        verify(stubSeshManager).terminate(Mockito.any());
    }

    @Test
    void testLoginCommand() {
        Command stubCommand = new LoginCommand(
                stubAuthService,
                stubSeshManager,
                stubUsername,
                stubPassword
        );
        stubCommand.execute(stubCtx);
        verify(stubAuthService).login(stubUsername, stubPassword);
        verify(stubSeshManager).issue(Mockito.any());
    }

    @Test
    void testGetBalanceCommand() {
        Command stubCommand = new GetBalanceCommand(
                stubIO,
                stubAccService,
                stubSeshManager
        );
        when(stubAccService.getBalance(stubId)).thenReturn(Money.ofMinor(0, "GBP"));
        stubCommand.execute(stubCtx);
        verify(stubIO).renderOutput(Mockito.anyString());
    }

    @Test
    void testDepositCommand() {
        Command stubCommand = new DepositCommand(
                5.5,
                stubAccService,
                stubSeshManager
        );
        stubCommand.execute(stubCtx);
    }
}
