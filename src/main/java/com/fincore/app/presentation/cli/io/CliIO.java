package com.fincore.app.presentation.cli.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CliIO implements InputProvider, OutputRenderer, PasswordReader {
    private final Scanner in;
    private final PrintStream out;

    public CliIO(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    @Override
    public String getStringInput(String prompt) {
        out.print(prompt);
        return in.nextLine().trim();
    }

    @Override
    public void print(String output) {
        out.println(output);
    }

    @Override
    public char[] getPasswordInput(String prompt) {
        out.print(prompt);
        return System.console().readPassword();
    }
}
