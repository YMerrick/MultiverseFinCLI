package com.fincore.app.menu.model;

import com.fincore.app.application.auth.AuthContext;
import lombok.NonNull;

import java.util.function.Consumer;

public class LoginItem extends MenuItem{
    public LoginItem(MenuDirective directive, @NonNull String label, MenuGroup subMenuGroup, Consumer<AuthContext> command) {
        super(directive, label, subMenuGroup, command);
    }

    @Override
    public MenuResponse run(AuthContext ctx) {
        try {
            return super.run(ctx);
        } catch (Exception e) {
            return new MenuResponse(MenuDirective.STAY, null);
        }
    }
}
