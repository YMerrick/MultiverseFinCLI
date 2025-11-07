package com.fincore.app.menu.nav;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.menu.model.*;
import lombok.Getter;

import java.util.*;

public class MenuNavigator {
    private final Deque<MenuGroup> menuGroupStack;
    @Getter
    private boolean isExit;
    private MenuGroup currentMenu;
    private final MenuRenderer renderer;

    public MenuNavigator(MenuGroup root, MenuRenderer renderer, Deque<MenuGroup> stack) {
        this.menuGroupStack = stack;
        menuGroupStack.push(root);
        this.isExit = false;
        currentMenu = root;
        this.renderer = renderer;
    }

    public MenuNavigator(MenuGroup root, MenuRenderer renderer) {
        this(root, renderer, new ArrayDeque<>());
    }

    public void interpretDirective(MenuResponse res) {
        if (Objects.isNull(res.directive())) throw new IllegalStateException("Directive is missing");
        switch (res.directive()) {
            case EXIT -> isExit = true;
            case BACK -> {
                menuGroupStack.pop();
                currentMenu = menuGroupStack.peek();
            }
            case GOTO_MENU -> {
                moveToMenu(res);
                currentMenu = menuGroupStack.peek();
            }
            case STAY -> {}
        }
    }

    public MenuResponse select(int choice , AuthContext ctx) throws IndexOutOfBoundsException {
        return currentMenu.getItem(choice).run(ctx);
    }

    public String render() {
        List<String> menuStack = menuGroupStack.stream().map(MenuGroup::getLabel).toList();
        List<String> menuLabels = currentMenu.getItemLabels();

        return renderer.render(menuStack, menuLabels);
    }

    private void moveToMenu(MenuResponse response) {
        menuGroupStack.push(response.submenu().orElseThrow(IllegalStateException::new));
    }
}
