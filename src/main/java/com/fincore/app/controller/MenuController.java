package com.fincore.app.controller;

import com.fincore.app.menu.CLIMenuComponent;
import com.fincore.app.menu.CLIMenuGroup;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class MenuController {
    private final Deque<CLIMenuGroup> menuStack;

    public MenuController(CLIMenuGroup root) {
        this.menuStack = new ArrayDeque<CLIMenuGroup>();
        menuStack.push(root);
    }

    public void start() {
        Scanner sysIn = new Scanner(System.in);
        // Menu Loop
        for (;;) {

        }
    }

    public int getInput(Scanner inStream, int menuSize) {
        return 1;
    }

    public void renderMenu() {
        CLIMenuComponent currentMenu = menuStack.peek();

        // Get all options

        // Compose the out string
    }

    public void callChild(int index) {
        // Add menu component on to the stack
    }
}
