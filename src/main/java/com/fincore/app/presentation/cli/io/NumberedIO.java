package com.fincore.app.presentation.cli.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberedIO implements IOHandler, PasswordReader, InputProvider, OutputRenderer {
    private final PrintStream out;
    private final Scanner reader;


    public NumberedIO(PrintStream out, InputStream in) {
        this.out = out;
        reader = new Scanner(in);
    }

    public void renderMenu(List<String> childrenLabels) {
        ArrayList<String> stringListToBeOutput = new ArrayList<>();

        stringListToBeOutput.add(renderMenuOptions(childrenLabels));
        stringListToBeOutput.add("");

        this.out.print(String.join("\n", stringListToBeOutput));
    }

    public void renderSpaces() {
        this.out.println("\n\n\n");
    }

    public void renderBreadcrumb(List<String> menuStack) {
        this.out.println(createBreadcrumbPath(menuStack));
    }

    private static String createBreadcrumbPath(List<String> menuStack) {
        return String.join(" > ", menuStack.reversed());
    }

    private String renderMenuOptions(List<String> labels) {
        return IntStream.range(0, labels.size())
                .mapToObj(i -> i + ". " + labels.get(i))
                .collect(Collectors.joining("\n"));
    }

    public String getInput(String prompt) {
        this.out.print(prompt);
        return reader.nextLine().trim();
    }

    public char[] getPasswordInput(String prompt) {
        this.out.print(prompt);
        return System.console().readPassword();
    }

    public void renderOutput(String output) {
        this.out.println(output);
    }
}
