package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.AuthContext;

public interface Command {
    void execute(AuthContext ctx);
}
