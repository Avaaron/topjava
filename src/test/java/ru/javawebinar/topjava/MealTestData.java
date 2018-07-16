package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int AUHT_USER = 100000;


    public static  Meal MEAL1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "завтрак", 500);
    public static  Meal MEAL2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "обед", 1000);
    public static  Meal MEAL3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "ужин", 500);
    public static  Meal MEAL4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "завтрак", 500);
    public static  Meal MEAL5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "обед", 1000);
    public static  Meal MEAL6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "ужин", 510);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id", "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }


}
