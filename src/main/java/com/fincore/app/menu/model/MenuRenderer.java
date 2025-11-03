package com.fincore.app.menu.model;

import java.util.List;

public interface MenuRenderer {
    String render(List<String> menuStack);
}
