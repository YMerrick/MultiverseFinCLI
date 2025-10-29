package com.fincore.app.cli.app;

import com.fincore.app.cli.menu.*;
import com.fincore.app.model.menu.MenuComponent;
import com.fincore.app.model.menu.MenuItemRunnable;
import lombok.Setter;

import java.util.*;

public class MenuController {
    private final Deque<Menu> menuStack = new ArrayDeque<Menu>();
    @Setter
    private UUID currentSessionToken;

    public MenuController(Menu root) {
        menuStack.push(root);
    }

    public void start() {
        Menu currentMenu;
        int userInput;

        for (;;) {
            currentMenu = menuStack.peek();
            if (Objects.isNull(currentMenu)) return;

            currentMenu.render(menuStack.stream().map(MenuComponent::getLabel).toList());
            userInput = currentMenu.getMenuChoice();

            MenuResponse res = callChild(currentMenu.getItem(userInput - 1));
            switch (res.directive()) {
                case EXIT -> {return;}
                case BACK -> menuStack.pop();
                case GOTO_MENU -> {
                    if (res.submenu().isEmpty()) throw new RuntimeException();
                    menuStack.push(res.submenu().get());
                }
                case STAY -> {}
            }

        }
    }

    private MenuResponse callChild(MenuItemRunnable child) {
        return child.run();
    }
}
