package com.fincore.app;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.*;
import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.data.inmemory.*;
import com.fincore.app.data.security.*;
import com.fincore.app.menu.model.MenuItem;
import com.fincore.app.domain.account.AccountStore;
import com.fincore.app.domain.identity.CredentialStore;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.identity.SessionStore;
import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.loop.CliLoop;
import com.fincore.app.presentation.cli.port.CliMenuRenderer;

import static com.fincore.app.menu.model.MenuDirective.*;

public class Main {
    public static void main(String[] args) {
        CliIO io = new CliIO(System.in, System.out);
        CommandFactory actionFactory = getActionFactory(io);

        MenuItem exit = new MenuItem(
                "Exit",
                actionFactory.createTraversal(
                        EXIT,
                        null,
                        null
                )
        );

        MenuItem back = new MenuItem(
                "Back",
                actionFactory.createTraversal(
                        BACK,
                        null,
                        null
                )
        );

        MenuItem logout = new MenuItem(
                "Logout",
                actionFactory.createLogout()
        );

        MenuItem register = new MenuItem(
                "Register",
                actionFactory.createRegister()
        );

        MenuItem deposit = new MenuItem(
                "Deposit",
                actionFactory.createDeposit()
        );

        MenuItem withdraw = new MenuItem(
                "Withdraw",
                actionFactory.createWithdraw()
        );

        MenuItem displayBalance = new MenuItem(
                "Display Balance",
                actionFactory.createDisplayBalance()
        );

        MenuGroup accountMenuGroup = new MenuGroup("Account");
        MenuItem goToAccMenu = new MenuItem(
                "Accounts",
                actionFactory.createTraversal(
                        GOTO_MENU,
                        accountMenuGroup,
                        null
                )
        );

        accountMenuGroup.addMenuItem(back);
        accountMenuGroup.addMenuItem(deposit);
        accountMenuGroup.addMenuItem(withdraw);
        accountMenuGroup.addMenuItem(displayBalance);


        MenuGroup mainMenuGroup = new MenuGroup("Main Menu");
        mainMenuGroup.addMenuItem(logout);
        mainMenuGroup.addMenuItem(goToAccMenu);

        MenuGroup authMenuGroup = new MenuGroup("User");

        MenuAction loginAction = actionFactory.createLogin(mainMenuGroup);

        MenuItem login = new MenuItem(
                "Login",
                loginAction
        );

        authMenuGroup.addMenuItem(exit);
        authMenuGroup.addMenuItem(login);
        authMenuGroup.addMenuItem(register);

        AuthContext ctx = new AuthContext();
        MenuNavigator navigator = new MenuNavigator(authMenuGroup, new CliMenuRenderer());
        CliLoop.run(navigator, io, ctx);
    }

    private static CommandFactory getActionFactory(CliIO io) {
        SessionStore sessionStore = new InMemorySessionStore();
        AccountStore accountStore = new InMemoryAccountStore();
        CredentialStore credRepo = new InMemoryCredentialStore();
        PasswordHasher hasher = new BCryptHasher();

        SessionManager sessionManager = new SessionManager(sessionStore);
        AccountService accountService = new AccountService(accountStore);
        AuthService authService = new AuthService(credRepo, hasher);

        return new CommandFactory(
                io,
                io,
                authService,
                sessionManager,
                accountService
        );
    }
}