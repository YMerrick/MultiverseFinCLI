package com.fincore.app.model.common;

public final class MoneyFormatter {
    public static String format(Money m) {
        return String.format(
                "%s%.2f",
                m.getCurrency().getSymbol(), m.asMajorUnits().doubleValue()
        );
    }
}
