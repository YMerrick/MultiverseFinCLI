package com.fincore.app.cli.io;

import com.fincore.app.cli.menu.CLIMenuGroup;

import java.util.Deque;
import java.util.List;

public interface IOHandler {
    char[] getPasswordInput();
    String getInput();
    void renderMenu(List<String> menuStack, List<String> childrenLabels);
}
