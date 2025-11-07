package com.fincore.app.menu.model;

import java.util.Objects;
import java.util.Optional;

public class MenuResponseBuilder {
    private MenuDirective directive = MenuDirective.STAY;
    private MenuGroup submenu = null;
    private String message = null;

    public MenuResponseBuilder directive(MenuDirective directive) {
        if (Objects.isNull(directive)) throw new IllegalArgumentException("Directive can not be null");
        this.directive = directive;
        return this;
    }

    public MenuResponseBuilder submenu(MenuGroup submenu) {
        this.submenu = submenu;
        return this;
    }

    public MenuResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public MenuResponse build() {
        return new MenuResponse(
                directive,
                Optional.ofNullable(submenu),
                Optional.ofNullable(message)
        );
    }

}
