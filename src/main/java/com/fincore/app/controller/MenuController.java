package com.fincore.app.controller;

import com.fincore.app.menu.CLIMenuComponent;
import com.fincore.app.menu.CLIMenuGroup;
import com.fincore.app.menu.MenuDirective;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MenuController {
    private final Deque<CLIMenuGroup> menuStack = new ArrayDeque<CLIMenuGroup>();;

    public MenuController(CLIMenuGroup root) {
        menuStack.push(root);
    }

    public void start() {
        Scanner sysIn = new Scanner(System.in);
        CLIMenuGroup currentMenu;
        int userInput;
        MenuDirective directive;

        for (;;) {
            currentMenu = menuStack.peek();
            if (Objects.isNull(currentMenu)) return;

            System.out.print(renderMenu(currentMenu));
            userInput = getInput(sysIn, currentMenu.getMenuSize());
            directive = interpretCommandOrExit(userInput, currentMenu);
            switch (directive) {
                case EXIT -> {return;}
                case BACK -> menuStack.pop();
                case GOTO_CHILD, STAY -> {}
            }

        }
    }

    private int getInput(Scanner inStream, int menuSize) {
        int userInput = -1;
        String line;

        while (true) {
            line = inStream.nextLine().trim();
            try {
                userInput = Integer.parseInt(line);
                if ((userInput >= 0) && (userInput <= menuSize)) return userInput;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter a valid option: ");
        }
    }

    private String createBreadcrumbPath() {
        return menuStack.reversed()
                .stream()
                .map(CLIMenuGroup::render)
                .collect(Collectors.joining(" > "));
    }

    private String renderBackOrExit() {
        return "0. " + (menuStack.size() > 1 ? "Back" : "Exit");
    }

    private String renderInputPrompt() {
        return "Please enter a number corresponding to your choice: ";
    }

    private String renderMenuOptions(CLIMenuGroup currentMenu) {
        ArrayList<String> menuList = new ArrayList<>(currentMenu.getChildrenLabels());
        return IntStream.range(0, currentMenu.getMenuSize())
                .mapToObj(i -> i+1 + ". " + menuList.get(i))
                .collect(Collectors.joining("\n"));
    }

    private String renderMenu(CLIMenuGroup currentMenu) {
        // Get all options
        ArrayList<String> stringListToBeOutput = new ArrayList<String>();
        String outputBody;

        stringListToBeOutput.add(createBreadcrumbPath());

        stringListToBeOutput.add(renderMenuOptions(currentMenu));

        stringListToBeOutput.add(renderBackOrExit());
        stringListToBeOutput.add("");
        stringListToBeOutput.add(renderInputPrompt());

        outputBody = String.join("\n", stringListToBeOutput);

        return outputBody;
    }

    private MenuDirective interpretCommandOrExit(int choice, CLIMenuGroup currentMenu) {
        if (choice == 0) {
            if (currentMenu.getMenuSize() > 1) return MenuDirective.BACK;
            return MenuDirective.EXIT;
        }
        return callChild(choice - 1, currentMenu);
    }

    private MenuDirective callChild(int index, CLIMenuGroup currentMenu) {
        if (index < 0 || index >= currentMenu.getMenuSize()) return MenuDirective.STAY;
        CLIMenuComponent child = currentMenu.getChild(index);
        if (child.isGroup()) menuStack.push((CLIMenuGroup) child);

        return child.select();
    }
}
