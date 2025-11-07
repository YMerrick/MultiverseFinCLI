package com.fincore.app.boot;

import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.model.MenuItem;

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

        return authMenuGroup;
    }
}
