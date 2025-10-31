package com.fincore.app.menu;

import com.fincore.app.application.auth.Context;

import java.util.*;

public class MenuNavigator {
    private final Deque<MenuGroup> menuGroupStack = new ArrayDeque<>();
    private final Context ctx;

    public MenuNavigator(MenuGroup root, Context ctx) {
        menuGroupStack.push(root);
        this.ctx = ctx;
    }

    public void start() {
        MenuGroup currentMenuGroup;
        int userInput;
        MenuResponse res;

        for (;;) {
            currentMenuGroup = menuGroupStack.peek();
            if (Objects.isNull(currentMenuGroup)) return;

            currentMenuGroup.render(menuGroupStack.stream().map(MenuComponent::getLabel).toList());
            userInput = currentMenuGroup.getMenuChoice();

            res = callChild(currentMenuGroup.getItem(userInput), ctx);
            switch (res.directive()) {
                case EXIT -> {return;}
                case BACK -> menuGroupStack.pop();
                case GOTO_MENU -> moveToMenu(res);
                case STAY -> {}
            }

        }
    }

    private MenuResponse callChild(MenuAction child, Context ctx) {
        return child.run(ctx);
    }

    private void moveToMenu(MenuResponse response) {
        menuGroupStack.push(response.submenu().orElseThrow(RuntimeException::new));
    }
}
