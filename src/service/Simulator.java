package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private final Configuration configuration;
    private List<StepState> stepStates;
    private List<VariableValue> variables;
    private List<Transaction> transactions;

    public Simulator(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Transaction> run() {
        setup();
        simulate();
        return transactions;
    }

    private void setup() {
        stepStates = new ArrayList<>();
        for (StepDefinition stepDefinition : configuration.getStepDefinitions()) {
            stepStates.add(new StepState(stepDefinition, configuration.getScope().getStartDate()));
        }

        variables = new ArrayList<>();
        for (VariableDefinition variable : configuration.getVariableDefinitions()) {
            variables.add(new VariableValue(
                    variable.getName(),
                    variable.getStartValue()));
        }

        transactions = new ArrayList<>();
        transactions.add(new Transaction(
                configuration.getScope().getStartDate(),
                "Start Simulation",
                copyVariables(variables)));
    }

    private void simulate() {
        LocalDate currentDateBeingSimulated = configuration.getScope().getStartDate();
        LocalDate lastDateToBeSimulated = configuration.getScope().getEndDate();

        while (currentDateBeingSimulated.isBefore(lastDateToBeSimulated)) {
            simulateOneDay(currentDateBeingSimulated);
            currentDateBeingSimulated = currentDateBeingSimulated.plusDays(1);
        }
    }

    private void simulateOneDay(LocalDate currentDate) {
        for (StepState stepState : stepStates) {
            if (!stepState.getDateToRun().equals(currentDate)) {
                continue;
            }

            StepDefinition stepDefinition = stepState.getStepDefinition();
            
            for(VariableChange varChange:stepDefinition.getVariableChanges()) {
            	for (VariableValue varValue : variables) {
            		if(varValue.getName().equals(varChange.getVariableName())) {
            			varValue.addValue(varChange.getModifyBy());
            			break;
            		}
            	}
            	
            } 	

            transactions.add(new Transaction(
            		currentDate,
                    stepDefinition.getName(),
                    copyVariables(variables)));

            stepState.setDateToRun(stepState.getDateToRun().plusMonths(1));
        }
    }

    private List<VariableValue> copyVariables(List<VariableValue> variables) {
        List<VariableValue> copy = new ArrayList<>();
        for (VariableValue variable : variables) {
            copy.add(new VariableValue(variable));
        }
        return copy;
    }
}