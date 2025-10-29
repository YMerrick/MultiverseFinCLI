package com.fincore.app.cli.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberedIO implements IOHandler {
    private final PrintStream out;
    private final Scanner reader;


    public NumberedIO(PrintStream out, InputStream in) {
        this.out = out;
        reader = new Scanner(in);
    }

    public void renderMenu(List<String> childrenLabels) {
        // Get all options
        ArrayList<String> stringListToBeOutput = new ArrayList<String>();
        String outputBody;
        stringListToBeOutput.addAll(List.of("", "", "", ""));

        stringListToBeOutput.add(renderMenuOptions(childrenLabels));

        stringListToBeOutput.add("");
        stringListToBeOutput.add(renderInputPrompt());

        outputBody = String.join("\n", stringListToBeOutput);

        this.out.print(outputBody);
    }

    public void renderBreadcrumb(List<String> menuStack) {
        out.println(createBreadcrumbPath(menuStack));
    }

    private static String createBreadcrumbPath(List<String> menuStack) {
        return String.join(" > ", menuStack.reversed());
    }

    private String renderMenuOptions(List<String> labels) {
        return IntStream.range(0, labels.size())
                .mapToObj(i -> i+1 + ". " + labels.get(i))
                .collect(Collectors.joining("\n"));
    }

    private String renderReturnLabel(int stackSize) {
        String terminationLabel = switch (stackSize) {
            case 1 -> "Exit";
            case 2 -> "Logout";
            default -> "Back";
        };
        return "0. " + terminationLabel;
    }

    private String renderInputPrompt() {
        return "Please enter a menu index corresponding to your choice: ";
    }

    public String getInput() {
        return reader.nextLine().trim();
    }

    public char[] getPasswordInput() {
        return System.console().readPassword();
    }

    public void renderOutput(String output) {
        System.out.println(output);
    }
}
