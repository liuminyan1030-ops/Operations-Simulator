package model;

import java.time.LocalDate;

public class Scope {

    private LocalDate startDate;
    private LocalDate endDate;

    public Scope(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}