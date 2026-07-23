package model;

import java.time.LocalDate;

public class StepState {

    private String name;
    private LocalDate dateToRun;

    public StepState(String name, LocalDate dateToRun) {
        this.name = name;
        this.dateToRun = dateToRun;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateToRun() {
        return dateToRun;
    }

    public void setDateToRun(LocalDate dateToRun) {
        this.dateToRun = dateToRun;
    }
}