package com.fincore.app.menu;

import com.fincore.app.application.auth.Context;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Builder
@AllArgsConstructor
public class MenuItem implements MenuComponent, MenuAction {
    @Builder.Default
    MenuDirective directive = MenuDirective.STAY;

    @NonNull
    @Getter
    String label;

    MenuGroup subMenuGroup;

    Consumer<Context> command;

    @Override
    public MenuResponse run(Context ctx) {
        if (Objects.nonNull(command)) command.accept(ctx);
        return new MenuResponse(this.directive, Optional.ofNullable(this.subMenuGroup));
    }
}
