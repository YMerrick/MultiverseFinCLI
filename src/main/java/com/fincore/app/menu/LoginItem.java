package com.fincore.app.menu;

import com.fincore.app.application.auth.Context;
import lombok.NonNull;

import java.util.function.Consumer;

public class LoginItem extends MenuItem{
    public LoginItem(MenuDirective directive, @NonNull String label, MenuGroup subMenuGroup, Consumer<Context> command) {
        super(directive, label, subMenuGroup, command);
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
