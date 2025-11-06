package com.fincore.app.presentation.cli.port;

import com.fincore.app.menu.model.MenuRenderer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CliMenuRenderer implements MenuRenderer {

    // This describes how things are rendered to be displayed later
    @Override
    public String render(List<String> menuStack, List<String> labels) {
        String breadcrumb = String.join(
                " > ",
                menuStack.reversed()
        );

        String menuOptions = IntStream.range(0, labels.size())
                .mapToObj(i -> i + ". " + labels.get(i))
                .collect(Collectors.joining("\n"));

        return String.format("""
                %s
                %s""",
                breadcrumb, menuOptions);
    }
}
