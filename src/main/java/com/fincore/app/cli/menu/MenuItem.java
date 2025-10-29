package com.fincore.app.cli.menu;

import com.fincore.app.model.menu.MenuComponent;
import com.fincore.app.model.menu.MenuItemRunnable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@AllArgsConstructor
public class MenuItem implements MenuComponent, MenuItemRunnable {
    MenuDirective directive;
    @Getter
    String label;
    Menu subMenu;

    @Override
    public MenuResponse run() {
        return new MenuResponse(this.directive, Optional.ofNullable(this.subMenu));
    }
}
