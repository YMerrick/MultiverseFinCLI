package com.fincore.app.boot;

import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.loop.CliLoop;

public class Bootstrapper {
    public void start() {

        Services services = new ServicesFactory().create();
        CliLoop.run(services.navigator(), services.io(), services.ctx());
    }
}
