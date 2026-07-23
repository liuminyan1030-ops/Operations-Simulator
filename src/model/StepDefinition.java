package model;

public class StepDefinition {

    private String name;
    private String variableName;
    private int modifyBy;

    public StepDefinition(String name, String variableName, int modifyBy) {
        this.name = name;
        this.variableName = variableName;
        this.modifyBy = modifyBy;
    }

    public String getName() {
        return name;
    }

    public String getVariableName() {
        return variableName;
    }

    public int getModifyBy() {
        return modifyBy;
    }
}