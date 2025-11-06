package com.fincore.app.menu.model;

import java.util.Optional;

public record MenuResponse(MenuDirective directive, Optional<MenuGroup> submenu, Optional<String> message) {
}
