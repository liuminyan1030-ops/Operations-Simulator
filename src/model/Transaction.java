package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private LocalDate date;
    private String description;
    private List<VariableValue> variableValues;

    public Transaction(LocalDate date,
                       String description,
                       List<VariableValue> variableValues) {

        this.date = date;
        this.description = description;
        this.variableValues = variableValues;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<VariableValue> getVariableValues() {
        return variableValues;
    }

    public Transaction copy() {

        List<VariableValue> copy = new ArrayList<>();

        for (VariableValue value : variableValues) {
            copy.add(new VariableValue(value));
        }

        return new Transaction(date, description, copy);
    }
}