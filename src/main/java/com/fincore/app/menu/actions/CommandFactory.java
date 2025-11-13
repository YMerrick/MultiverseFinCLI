package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.accounts.UserService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuDirective;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.presentation.cli.io.InputProvider;
import com.fincore.app.presentation.cli.io.PasswordReader;
import lombok.AllArgsConstructor;

// Needs to worry only about creating commands
// Does not contain fields pertaining to services
// Pass the services to the methods instead
@AllArgsConstructor
public class CommandFactory {
    private InputProvider inputProvider;
    private PasswordReader passwordReader;
    private AuthService authService;
    private SessionManager sessionManager;
    private AccountService accountService;
    private UserService userService;

    public MenuAction createDeposit() {
        return new DepositAction(
                sessionManager,
                accountService,
                inputProvider
        );
    }

    public MenuAction createDisplayBalance() {
        return new DisplayBalanceAction(
                sessionManager,
                accountService
        );
    }

    public MenuAction createRegister() {
        return new RegisterAction(
                authService,
                userService,
                inputProvider,
                passwordReader
        );
    }

    public MenuAction createLogin(MenuGroup submenu) {
        return new LoginAction(
                inputProvider,
                passwordReader,
                submenu,
                authService,
                sessionManager
        );
    }

    public MenuAction createLogout() {
        return new LogoutAction(sessionManager);
    }

    public MenuAction createTraversal(MenuDirective directive, MenuGroup submenu, String message) {
        return new TraversalAction(
                directive,
                submenu,
                message
        );
    }

    public MenuAction createWithdraw() {
        return new WithdrawAction(
                sessionManager,
                accountService,
                inputProvider
        );
    }

    public MenuAction createAccount() {
        return new CreateAccountAction(
                inputProvider,
                accountService,
                sessionManager,
                userService
        );
    }

    public MenuAction createDisplayAccount() {
        return new DisplayAccountAction(
                accountService,
                sessionManager
        );
    }
}
