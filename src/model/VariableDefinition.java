package model;

public class VariableDefinition {
    private String name;
    private int startValue;
    private Unit unit; 

    public VariableDefinition(String name, int startValue, Unit unit) {
        this.name = name;
        this.startValue = startValue;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getStartValue() {
        return startValue;
    }

    public Unit getUnit() { 
        return unit;
    }
}