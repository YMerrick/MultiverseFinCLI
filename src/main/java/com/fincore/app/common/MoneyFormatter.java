package com.fincore.app.common;

public final class MoneyFormatter {
    public static String format(Money m) {
        return m.getCurrency() + m.asMajorUnits().toString();
    }
}
