package model;

import java.util.List;

public class StepDefinition {

    private String name;
    private List<VariableChange> variableChanges;

    public StepDefinition(String name, List<VariableChange> variableChanges) {
        this.name = name;
        this.variableChanges=variableChanges;
    }

    public String getName() {
        return name;
    }

    public List<VariableChange> getVariableChanges() {
        return variableChanges;
    }

 
}