package com.fincore.app.menu.model;

import com.fincore.app.application.auth.AuthContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class MenuItem implements MenuComponent {
    @NonNull
    @Getter
    private String label;
    private MenuAction action;

    public MenuResponse run(AuthContext ctx) {
        return action.run(ctx);
    }
}
