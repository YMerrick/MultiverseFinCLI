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
        int stackSize;

        for (;;) {
            currentMenu = menuStack.peek();
            stackSize = menuStack.size();
            if (Objects.isNull(currentMenu)) throw new NullPointerException("Menu does not exist");

            System.out.print(renderMenu(currentMenu));
            userInput = getInput(sysIn, currentMenu.getMenuSize());
            directive = interpretCommandOrExit(userInput, stackSize, currentMenu);
            switch (directive) {
                case EXIT -> System.exit(0);
                case BACK -> menuStack.pop();
                case GOTO_CHILD, STAY -> {}
            }

        }
    }

    private int getInput(Scanner inStream, int menuSize) {
        int userInput = -1;
        boolean isValid = false;
        
        while (!isValid) {
            if (!inStream.hasNextInt()) {
                System.out.print("Please enter a valid option: ");
                inStream.next();
                continue;
            }
            
            userInput = inStream.nextInt();
            if (userInput < 0 || userInput > menuSize) {
                System.out.print("Please enter a valid option: ");
                inStream.next();
                continue;
            }
            
            isValid = true;
        }
        return userInput;
    }

    private String createBreadcrumbPath() {
        return menuStack.reversed()
                .stream()
                .map(CLIMenuGroup::render)
                .collect(Collectors.joining(" > "));
    }

    private String renderBackOrExit() {
        String output = "0. ";
        if (menuStack.size() > 1) output += "Back";
        else output += "Exit";
        return output;
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

    private MenuDirective interpretCommandOrExit(int choice, int stackSize, CLIMenuGroup currentMenu) {
        if (choice == 0) {
            if (stackSize > 1) return MenuDirective.BACK;
            return MenuDirective.EXIT;
        }
        return callChild(choice - 1, currentMenu);
    }

    private MenuDirective callChild(int index, CLIMenuGroup currentMenu) {
        // Add menu component on to the stack
        CLIMenuComponent child = currentMenu.getChild(index);
        if (child.isGroup()) menuStack.push((CLIMenuGroup) child);

        return child.select();
    }
}
