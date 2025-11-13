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

    public MenuAction createAction(ActionType type, MenuGroup submenu) {
        return switch(type) {
            case LOGIN -> new LoginAction(
                    inputProvider,
                    passwordReader,
                    submenu,
                    authService,
                    sessionManager
            );
            case LOGOUT -> new LogoutAction(sessionManager);
            case DEPOSIT -> new DepositAction(
                    sessionManager,
                    accountService,
                    inputProvider
            );
            case WITHDRAW -> new WithdrawAction(
                    sessionManager,
                    accountService,
                    inputProvider
            );
            case SELECT_ACC -> new SelectAccountAction(
                    accountService,
                    sessionManager,
                    inputProvider
            );
            case DISPLAY_ACC -> new DisplayAccountAction(
                    accountService,
                    sessionManager
            );
            case DISPLAY_BAL -> new DisplayBalanceAction(
                    sessionManager,
                    accountService
            );
            case CREATE_ACCOUNT -> new CreateAccountAction(
                    inputProvider,
                    accountService,
                    sessionManager,
                    userService
            );
            case REGISTER_USER -> new RegisterAction(
                    authService,
                    userService,
                    inputProvider,
                    passwordReader
            );
        };
    }

    public MenuAction createTraversal(MenuDirective directive, MenuGroup submenu, String message) {
        return new TraversalAction(
                directive,
                submenu,
                message
        );
    }
}
