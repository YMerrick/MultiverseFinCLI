package com.fincore.app.cli.io;


import java.util.List;

public interface IOHandler {
    char[] getPasswordInput();
    String getInput();
    void renderMenu(List<String> childrenLabels);
    void renderOutput(String output);
    void renderBreadcrumb(List<String> menuStack);
}
