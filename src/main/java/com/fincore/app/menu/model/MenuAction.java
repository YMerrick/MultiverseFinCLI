package com.fincore.app.menu.model;

import com.fincore.app.application.auth.Context;

import java.util.Optional;

public interface MenuAction {
    default MenuResponse run(Context ctx) {
        return new MenuResponse(MenuDirective.STAY, Optional.empty());
    }
}
