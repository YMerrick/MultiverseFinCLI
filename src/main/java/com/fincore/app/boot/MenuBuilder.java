package com.fincore.app.boot;

import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.model.MenuItem;

import static com.fincore.app.menu.actions.ActionType.*;
import static com.fincore.app.menu.model.MenuDirective.*;

public class MenuBuilder {
    public MenuGroup build(CommandFactory actionFactory) {
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
                actionFactory.createAction(LOGOUT, null)
        );

        MenuItem register = new MenuItem(
                "Register",
                actionFactory.createAction(REGISTER_USER, null)
        );

        MenuItem deposit = new MenuItem(
                "Deposit",
                actionFactory.createAction(DEPOSIT, null)
        );

        MenuItem withdraw = new MenuItem(
                "Withdraw",
                actionFactory.createAction(WITHDRAW, null)
        );

        MenuItem displayBalance = new MenuItem(
                "Display Balance",
                actionFactory.createAction(DISPLAY_BAL, null)
        );

        MenuItem makeAccount = new MenuItem(
                "Create Account",
                actionFactory.createAction(CREATE_ACCOUNT, null)
        );

        MenuItem displayAccounts = new MenuItem(
                "Display Accounts",
                actionFactory.createAction(DISPLAY_ACC, null)
        );

        MenuItem selectAccount = new MenuItem(
                "Select Account",
                actionFactory.createAction(SELECT_ACC, null)
        );

        MenuGroup accountMenuGroup = new MenuGroup("Account");
        MenuGroup transactionMenu = new MenuGroup("Transactions");

        MenuItem goToTransMenu = new MenuItem(
                "Transactions",
                actionFactory.createTraversal(
                        GOTO_MENU,
                        transactionMenu,
                        null
                )
        );

        MenuItem goToAccMenu = new MenuItem(
                "Accounts",
                actionFactory.createTraversal(
                        GOTO_MENU,
                        accountMenuGroup,
                        null
                )
        );

        transactionMenu.addMenuItem(back);
        transactionMenu.addMenuItem(deposit);
        transactionMenu.addMenuItem(withdraw);
        transactionMenu.addMenuItem(displayBalance);

        accountMenuGroup.addMenuItem(back);
        accountMenuGroup.addMenuItem(displayAccounts);
        accountMenuGroup.addMenuItem(makeAccount);
        accountMenuGroup.addMenuItem(selectAccount);


        MenuGroup mainMenuGroup = new MenuGroup("Main Menu");
        mainMenuGroup.addMenuItem(logout);
        mainMenuGroup.addMenuItem(goToAccMenu);
        mainMenuGroup.addMenuItem(goToTransMenu);

        MenuGroup authMenuGroup = new MenuGroup("User");

        MenuItem login = new MenuItem(
                "Login",
                actionFactory.createAction(
                        LOGIN,
                        mainMenuGroup
                )
        );

        authMenuGroup.addMenuItem(exit);
        authMenuGroup.addMenuItem(login);
        authMenuGroup.addMenuItem(register);

        return authMenuGroup;
    }
}
