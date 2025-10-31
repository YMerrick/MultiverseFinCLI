package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.Context;

public interface Command {
    void execute(Context ctx);
}
