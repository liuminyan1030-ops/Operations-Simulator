package model;

import java.util.List;

public class Configuration {

    private Scope scope;
    private List<VariableDefinition> variableDefinitions;
    private List<StepDefinition> stepDefinitions;

    public Configuration(
            Scope scope,
            List<VariableDefinition> variableDefinitions,
            List<StepDefinition> stepDefinitions) {

        this.scope = scope;
        this.variableDefinitions = variableDefinitions;
        this.stepDefinitions = stepDefinitions;
    }

    public Scope getScope() {
        return scope;
    }

    public List<VariableDefinition> getVariableDefinitions() {
        return variableDefinitions;
    }

    public List<StepDefinition> getStepDefinitions() {
        return stepDefinitions;
    }
}