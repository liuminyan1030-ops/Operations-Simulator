package model;

public class VariableDefinition {

    private String name;
    private int startValue;

    public VariableDefinition(String name, int startValue) {
        this.name = name;
        this.startValue = startValue;
    }

    public String getName() {
        return name;
    }

    public int getStartValue() {
        return startValue;
    }
}