package model;

public enum Unit {
    MONEY("Money"),
    QUANTITY("Quantity"),
    NONE("");

    private final String displayValue;

    Unit(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return this.displayValue;
    }
}