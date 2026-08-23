package model;


public class VariableChange {
    private String variableName;
    private int modifyBy;

    public VariableChange(String variableName, int modifyBy) {
        this.variableName = variableName;
        this.modifyBy = modifyBy;
    }

    public String getVariableName() {
        return variableName;
    }

    public int getModifyBy() {
        return modifyBy;
    }
}
