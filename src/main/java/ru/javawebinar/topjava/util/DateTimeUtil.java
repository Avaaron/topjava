package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static List<String> hours =  new ArrayList<>();

    static {
        for (int i = 0; i < 24; i++) {
            String h = i + "";
            if (h.length() < 2) h = "0" + h;
            hours.add(h + ":00");
        }
    }

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



}
