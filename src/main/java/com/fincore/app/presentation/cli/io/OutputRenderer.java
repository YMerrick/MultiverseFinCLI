package com.fincore.app.presentation.cli.io;

import java.util.List;

public interface OutputRenderer {
    void renderMenu(List<String> childrenLabels);
    void renderOutput(String output);
    void renderBreadcrumb(List<String> menuStack);
    void renderSpaces();
}
