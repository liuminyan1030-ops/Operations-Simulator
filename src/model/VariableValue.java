package model;

public class VariableValue {

    private String name;
    private int value;

    public VariableValue(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public VariableValue(VariableValue other) {
        this.name = other.name;
        this.value = other.value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void addValue(int amount) {
        value += amount;
    }
}