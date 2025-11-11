package com.fincore.app.boot;

import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.loop.CliLoop;

public class Bootstrapper {
    public void start() {

        Services services = new ServicesFactory().create();

        CliIO io = new CliIO(System.in, System.out);
        CommandFactory commandFactory = new CommandFactory(
                io,
                io,
                services.authService(),
                services.sessionManager(),
                services.accountService(),
                services.userService()
        );
        MenuGroup root = new MenuBuilder().build(commandFactory);
        MenuNavigator nav = new MenuNavigator(root, services.renderer());

        CliLoop.run(nav, io, services.ctx());
    }
}
