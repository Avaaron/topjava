package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);


    List<Meal> getAll(int userId);

    List<Meal> filter(int userId, LocalDate startLocaldate, LocalDate endLocalDate, LocalTime startLocalTime, LocalTime endLocalTime);
}