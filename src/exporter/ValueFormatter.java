package exporter;

import model.Unit;

public class ValueFormatter {

    public String formatValueByUnit(int value, Unit unit) {
        if (unit == Unit.MONEY) {
            return String.format("$%.2f", (double) value);
        }
        return String.valueOf(value);
    }
}