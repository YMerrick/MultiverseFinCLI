package com.fincore.app.cli.app;

import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.cli.menu.CLIMenuComponent;
import com.fincore.app.cli.menu.CLIMenuGroup;
import com.fincore.app.cli.menu.MenuDirective;

import java.util.*;

public class MenuController {
    private final Deque<CLIMenuGroup> menuStack = new ArrayDeque<CLIMenuGroup>();
    private UUID currentSessionToken;

    public MenuController(CLIMenuGroup root) {
        menuStack.push(root);
    }

    public void start() {
        CLIMenuGroup currentMenu;
        int userInput;
        MenuDirective directive;

        for (;;) {
            currentMenu = menuStack.peek();
            if (Objects.isNull(currentMenu)) return;

            currentMenu.render(menuStack.stream().map(CLIMenuComponent::getLabel).toList());
            userInput = currentMenu.getMenuChoice();
            directive = interpretCommandOrExit(userInput, currentMenu);
            switch (directive) {
                case EXIT -> {return;}
                case BACK -> menuStack.pop();
                case GOTO_CHILD -> {
                    CLIMenuGroup child = (CLIMenuGroup) currentMenu.getChild(userInput - 1);
                    menuStack.push(child);
                }
                case STAY -> {}
            }

        }
    }

    private MenuDirective interpretCommandOrExit(int choice, CLIMenuGroup currentMenu) {
        if (choice == 0) {
            if (menuStack.size() > 1) return MenuDirective.BACK;
            return MenuDirective.EXIT;
        }
        return callChild(choice - 1, currentMenu);
    }

    private MenuDirective callChild(int index, CLIMenuGroup currentMenu) {
        if (index < 0 || index >= currentMenu.getMenuSize()) return MenuDirective.STAY;
        CLIMenuComponent child = currentMenu.getChild(index);
        if (child.isGroup()) return MenuDirective.GOTO_CHILD;
        child.select();
        return MenuDirective.STAY;
    }
}
