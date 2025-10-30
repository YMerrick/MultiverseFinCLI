package com.fincore.app.cli.menu;

import com.fincore.app.application.auth.Context;
import com.fincore.app.cli.command.Command;
import com.fincore.app.model.menu.MenuComponent;
import com.fincore.app.model.menu.MenuItemRunnable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Builder
@AllArgsConstructor
public class MenuItem implements MenuComponent, MenuItemRunnable {
    @Builder.Default
    MenuDirective directive = MenuDirective.STAY;

    @NonNull
    @Getter
    String label;

    Menu subMenu;

    Consumer<Context> command;

    @Override
    public MenuResponse run(Context ctx) {
        if (Objects.nonNull(command)) command.accept(ctx);
        return new MenuResponse(this.directive, Optional.ofNullable(this.subMenu));
    }
}
