package com.fincore.app.cli.menu;

import java.util.Optional;

public class subMenuTraverseItem extends MenuItem {

    public subMenuTraverseItem(String label, Menu submenu) {
        super(MenuDirective.GOTO_MENU, label, submenu);
    }

    @Override
    public MenuResponse run() {
        return new MenuResponse(MenuDirective.GOTO_MENU, Optional.of(super.subMenu));
    }

}
