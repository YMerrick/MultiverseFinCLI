package com.fincore.app.menu;

import java.util.Optional;

public record MenuResponse(MenuDirective directive, Optional<MenuGroup> submenu) {
}
