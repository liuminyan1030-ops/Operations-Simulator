package model;

import java.time.LocalDate;

public enum Frequency {
    MONTHSTART,
    MONTHEND,
    WEEKLY,
    DAILY;

    public LocalDate getFirstRunDate(LocalDate startDate) {
        switch (this) {
            case MONTHSTART:
                if (startDate.getDayOfMonth() == 1) {
                    return startDate;
                }
                return startDate.withDayOfMonth(1).plusMonths(1);
            case MONTHEND:
                return startDate.withDayOfMonth(startDate.lengthOfMonth());
            case WEEKLY:
                return startDate.plusDays(7);
            case DAILY:
                return startDate;
            default:
                throw new IllegalStateException("Unknown frequency: " + this);
        }
    }

    public LocalDate getNextRunDate(LocalDate currentRunDate) {
        switch (this) {
            case MONTHSTART:
                return currentRunDate.plusMonths(1);
            case MONTHEND:
                LocalDate nextMonth = currentRunDate.plusMonths(1);
                return nextMonth.withDayOfMonth(nextMonth.lengthOfMonth());
            case WEEKLY:
                return currentRunDate.plusDays(7);
            case DAILY:
                return currentRunDate.plusDays(1);
            default:
                throw new IllegalStateException("Unknown frequency: " + this);
        }
    }
}