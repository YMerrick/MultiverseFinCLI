package com.fincore.app.cli.app;

import com.fincore.app.application.auth.Context;
import com.fincore.app.cli.menu.*;
import com.fincore.app.model.menu.MenuComponent;
import com.fincore.app.model.menu.MenuItemRunnable;

import java.util.*;

public class MenuController {
    private final Deque<Menu> menuStack = new ArrayDeque<>();
    private final Context ctx = new Context();

    public MenuController(Menu root) {
        menuStack.push(root);
    }

    public void start() {
        Menu currentMenu;
        int userInput;
        MenuResponse res;

        for (;;) {
            currentMenu = menuStack.peek();
            if (Objects.isNull(currentMenu)) return;

            currentMenu.render(menuStack.stream().map(MenuComponent::getLabel).toList());
            userInput = currentMenu.getMenuChoice();

            res = callChild(currentMenu.getItem(userInput), ctx);
            switch (res.directive()) {
                case EXIT -> {return;}
                case BACK -> menuStack.pop();
                case GOTO_MENU -> moveToMenu(res);
                case STAY -> {}
            }

        }
    }

    private MenuResponse callChild(MenuItemRunnable child, Context ctx) {
        return child.run(ctx);
    }

    private void moveToMenu(MenuResponse response) {
        menuStack.push(response.submenu().orElseThrow(RuntimeException::new));
    }
}
