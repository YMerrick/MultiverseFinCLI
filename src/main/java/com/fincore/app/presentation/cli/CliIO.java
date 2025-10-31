package com.fincore.app.presentation.cli;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CliIO {
    private Scanner in;
    private PrintStream out;

    public CliIO(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }
}
