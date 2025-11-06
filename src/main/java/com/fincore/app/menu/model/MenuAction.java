package com.fincore.app.menu.model;

import com.fincore.app.application.auth.AuthContext;

import java.util.Optional;

public interface MenuAction {
    default MenuResponse run(AuthContext ctx) {
        return new MenuResponse(
                MenuDirective.STAY,
                Optional.empty(),
                Optional.empty()
        );
    }
}
