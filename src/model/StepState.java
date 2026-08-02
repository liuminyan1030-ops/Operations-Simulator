package model;

import java.time.LocalDate;

public class StepState {

    private final StepDefinition stepDefinition;
    private LocalDate dateToRun;

    public StepState(StepDefinition stepDefinition, LocalDate dateToRun) {
        this.stepDefinition = stepDefinition;
        this.dateToRun = dateToRun;
    }

    public StepDefinition getStepDefinition() {
        return stepDefinition;
    }

    public String getName() {
        return stepDefinition.getName();
    }

    public LocalDate getDateToRun() {
        return dateToRun;
    }

    public void setDateToRun(LocalDate dateToRun) {
        this.dateToRun = dateToRun;
    }
}