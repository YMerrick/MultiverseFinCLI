package com.fincore.app;

import com.fincore.app.cli.command.CommandHandler;
import com.fincore.app.cli.app.MenuController;
import com.fincore.app.cli.io.NumberedIO;
import com.fincore.app.cli.menu.Menu;
import com.fincore.app.cli.menu.MenuDirective;
import com.fincore.app.cli.menu.subMenuTraverseItem;
import com.fincore.app.model.account.Account;
import com.fincore.app.cli.command.CommandFactory;
import com.fincore.app.cli.menu.MenuItem;
import com.fincore.app.model.menu.MenuComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Account user = new Account(UUID.randomUUID(), "TestUser",10_000);
        CommandFactory factory = new CommandFactory();
        CommandHandler controller = new CommandHandler(factory);

        NumberedIO numberMenu = new NumberedIO(System.out, System.in);

        MenuItem exit = MenuItem.builder().subMenu(null).label("Exit").directive(MenuDirective.EXIT).build();

        MenuItem back = MenuItem.builder().subMenu(null).label("Back").directive(MenuDirective.BACK).build();


        List<MenuItem> mainMenuList = new ArrayList<>();

        Menu accountMenu = new Menu(
                "Account",
                numberMenu
        );
        MenuItem accSubMenuItem = MenuItem.builder().subMenu(accountMenu).label("Accounts").directive(MenuDirective.GOTO_MENU).build();
        accountMenu.addMenuItem(back);


        Menu mainMenu = new Menu(
                "Main Menu",
                mainMenuList,
                numberMenu
        );
        mainMenu.addMenuItem(back);
        MenuItem mainMenuItem = MenuItem.builder().subMenu(mainMenu).label("Login").directive(MenuDirective.GOTO_MENU).build();
        mainMenu.addMenuItem(accSubMenuItem);


        Menu authMenu = new Menu(
                "User",
                numberMenu
        );
        authMenu.addMenuItem(exit);
        authMenu.addMenuItem(mainMenuItem);

        MenuController menuRunner = new MenuController(authMenu);
        menuRunner.start();

    }
}