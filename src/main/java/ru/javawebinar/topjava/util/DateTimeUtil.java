package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDate (LocalDate currentLocalDate, LocalDate startLocalDate,
                                         LocalDate endLocalDate) {
        return currentLocalDate.compareTo(startLocalDate) >= 0 && currentLocalDate.compareTo(endLocalDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static <T extends LocalDateTime> boolean  isBetweenTotal(  T lt,  T startTime, T endTime) {

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

}
