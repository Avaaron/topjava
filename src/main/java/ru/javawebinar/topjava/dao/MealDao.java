package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface MealDao {

    public static List<Meal> mealListDB = new ArrayList<>(MealsUtil.meals);

    void create(LocalDateTime dateTime, String description, int calories);
    void update(int id, String Description, int calories);
    void delete(int id);
    void read(int id);
    Meal getOneById(int id);

}
