package model;

public class VariableDefinition {
    private String name;
    private int startValue;
    private String unit; 

    public VariableDefinition(String name, int startValue, String unit) {
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

    public String getUnit() { 
        return unit;
    }
}