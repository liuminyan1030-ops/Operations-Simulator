package model;

import java.util.List;

public class StepDefinition {

    private String name;
    private List<VariableChange> variableChanges;
    private Frequency frequency;

    public StepDefinition(String name, List<VariableChange> variableChanges,Frequency frequency) {
        this.name = name;
        this.variableChanges=variableChanges;
        this.frequency=frequency;
    }

    public String getName() {
        return name;
    }

    public List<VariableChange> getVariableChanges() {
        return variableChanges;
    }
    
    public Frequency getFrequency() {
    	return frequency;
    }

 
}