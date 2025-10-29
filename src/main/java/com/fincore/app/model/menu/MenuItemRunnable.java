package com.fincore.app.model.menu;

import com.fincore.app.cli.menu.MenuDirective;
import com.fincore.app.cli.menu.MenuResponse;

import java.util.Optional;

public interface MenuItemRunnable {
    default public MenuResponse run() {
        return new MenuResponse(MenuDirective.STAY, Optional.empty());
    }
}
