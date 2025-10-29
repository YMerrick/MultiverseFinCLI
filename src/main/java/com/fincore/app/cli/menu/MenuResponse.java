package com.fincore.app.cli.menu;

import java.util.Optional;

public record MenuResponse(MenuDirective directive, Optional<Menu> submenu) {
}
