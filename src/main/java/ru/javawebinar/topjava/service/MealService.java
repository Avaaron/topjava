package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {

    Meal create(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getRepositotyFilter (int userId, LocalDate startLocalDate, LocalDate endLocalDate, LocalTime startLocalTime, LocalTime endLocalTime);
}