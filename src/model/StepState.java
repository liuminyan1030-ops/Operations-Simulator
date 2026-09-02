package model;

import java.time.LocalDate;

public class StepState {

    private final StepDefinition stepDefinition;
    private LocalDate dateToRun;

    public StepState(StepDefinition stepDefinition, LocalDate startDate) {
        this.stepDefinition = stepDefinition;
        this.dateToRun = stepDefinition.getFrequency().getFirstRunDate(startDate);
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

    public void setNextRunDate() {
        this.dateToRun = stepDefinition.getFrequency().getNextRunDate(dateToRun);
    }
}