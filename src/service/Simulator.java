package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public List<Transaction> run(Configuration configuration) {

        List<StepState> stepStates = new ArrayList<>();

        for (StepDefinition step : configuration.getStepDefinitions()) {
            stepStates.add(new StepState(
                    step.getName(),
                    configuration.getScope().getStartDate()));
        }

        List<VariableValue> variables = new ArrayList<>();

        for (VariableDefinition variable :
                configuration.getVariableDefinitions()) {

            variables.add(new VariableValue(
                    variable.getName(),
                    variable.getStartValue()));
        }

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(
                configuration.getScope().getStartDate(),
                "Start Simulation",
                copyVariables(variables)));

        LocalDate current =
                configuration.getScope().getStartDate();

        LocalDate end =
                configuration.getScope().getEndDate();

        while (!current.isAfter(end)) {

            for (int i = 0; i < stepStates.size(); i++) {

                StepState state = stepStates.get(i);

                if (state.getDateToRun().equals(current)) {

                    StepDefinition step =
                            configuration.getStepDefinitions().get(i);

                    for (VariableValue value : variables) {

                        if (value.getName().equals(step.getVariableName())) {
                            value.addValue(step.getModifyBy());
                        }
                    }

                    transactions.add(new Transaction(
                            current,
                            step.getName(),
                            copyVariables(variables)));

                    state.setDateToRun(
                            state.getDateToRun().plusMonths(1));
                }
            }

            current = current.plusDays(1);
        }

        return transactions;
    }

    private List<VariableValue> copyVariables(List<VariableValue> variables) {

        List<VariableValue> copy = new ArrayList<>();

        for (VariableValue variable : variables) {
            copy.add(new VariableValue(variable));
        }

        return copy;
    }

}