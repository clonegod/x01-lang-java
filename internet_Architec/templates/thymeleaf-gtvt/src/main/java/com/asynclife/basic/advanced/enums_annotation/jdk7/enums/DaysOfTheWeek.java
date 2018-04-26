package com.asynclife.basic.advanced.enums_annotation.jdk7.enums;

public enum DaysOfTheWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public boolean isWeekend(DaysOfTheWeek day) {
        return (day == SATURDAY || day == SUNDAY);
    }
}
