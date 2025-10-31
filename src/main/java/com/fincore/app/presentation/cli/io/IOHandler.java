package com.fincore.app.presentation.cli.io;


import java.util.List;

public interface IOHandler {
    char[] getPasswordInput(String prompt);
    String getInput(String prompt);
    void renderMenu(List<String> childrenLabels);
    void renderOutput(String output);
    void renderBreadcrumb(List<String> menuStack);
    void renderSpaces();
}
