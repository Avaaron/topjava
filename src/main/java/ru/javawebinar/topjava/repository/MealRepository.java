package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    Meal get(int id);
    boolean delete(int id);

    Meal get(int id, int userId);

    Collection<Meal> getAll();
    List<Meal> getAll(int userId);
}