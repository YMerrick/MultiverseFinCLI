package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuDirective;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogoutAction implements MenuAction {
    private SessionManager sessionManager;

    @Override
    public MenuResponse run(AuthContext ctx) {
        sessionManager.terminate(ctx.getSession());
        ctx.setSession(null);
        return new MenuResponseBuilder()
                .directive(MenuDirective.BACK)
                .build();
    }
}
