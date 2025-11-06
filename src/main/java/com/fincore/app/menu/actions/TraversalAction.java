package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.menu.model.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TraversalAction implements MenuAction {
    @NonNull
    private MenuDirective directive;
    private MenuGroup submenu;
    private String message;

    @Override
    public MenuResponse run(AuthContext ctx) {
        return new MenuResponseBuilder()
                .directive(directive)
                .submenu(submenu)
                .message(message)
                .build();
    }
}
