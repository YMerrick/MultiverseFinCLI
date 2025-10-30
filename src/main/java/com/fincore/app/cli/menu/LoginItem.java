package com.fincore.app.cli.menu;

import com.fincore.app.application.auth.Context;
import lombok.NonNull;

import java.util.function.Consumer;

public class LoginItem extends MenuItem{
    public LoginItem(MenuDirective directive, @NonNull String label, Menu subMenu, Consumer<Context> command) {
        super(directive, label, subMenu, command);
    }

    @Override
    public MenuResponse run(Context ctx) {
        try {
            return super.run(ctx);
        } catch (Exception e) {
            return new MenuResponse(MenuDirective.STAY, null);
        }
    }
}
