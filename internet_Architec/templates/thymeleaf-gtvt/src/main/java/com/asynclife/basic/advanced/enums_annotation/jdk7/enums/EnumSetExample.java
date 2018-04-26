package com.asynclife.basic.advanced.enums_annotation.jdk7.enums;

import java.util.EnumSet;
import java.util.Set;

public class EnumSetExample {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        final Set< DaysOfTheWeek> enumSetAll = EnumSet.allOf(DaysOfTheWeek.class);
        final Set< DaysOfTheWeek> enumSetNone = EnumSet.noneOf(DaysOfTheWeek.class);
        final Set< DaysOfTheWeek> enumSetSome = EnumSet.of(DaysOfTheWeek.SUNDAY, DaysOfTheWeek.SATURDAY);
    }
}
