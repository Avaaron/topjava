package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public interface MealService {

    Meal create(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal);

    Collection<MealWithExceed> getAll(int userId);
}