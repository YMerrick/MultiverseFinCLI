package com.fincore.app.presentation.cli.loop;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.presentation.cli.io.CliIO;

public class CliLoop {
    public static void run(MenuNavigator nav, CliIO io, AuthContext ctx) {
        String view;
        int choice;
        MenuResponse res;

        while (!nav.isExit()) {
            // Get view/render menus
            view = nav.render();
            // output view/rendered menus
            io.print(view);
            // Gather user choice / input
            choice = io.getIntInput("Please enter your choice: ");
            // pass choice to navigator
            io.print("\n");
            try {
                res = nav.select(choice, ctx);
                nav.interpretDirective(res);
                // output response messages
                if (res.message().isPresent()) {
                    io.print("\n");
                    io.print(res.message().get());
                }
            } catch (IndexOutOfBoundsException e) {
                io.print("That is an invalid choice");
            }
            io.print("\n");
        }
    }
}
