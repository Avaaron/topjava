package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao{

    public static List<Meal> mealListDB = Collections.synchronizedList(new ArrayList<>(MealsUtil.meals));

    private static AtomicInteger idCounter = new AtomicInteger(6);

    @Override
    public void create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(idCounter.incrementAndGet());
        mealListDB.add(meal);
    }

    @Override
    public void update(int id, LocalDateTime dateTime, String description, int calories) {
        for ( Meal meal : mealListDB) {
            if (meal.getId() == id) {
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
            }
        }
    }

    @Override
    public void delete(int id) {
        int i = 0;
        Meal meal = null;
        while (i < mealListDB.size()) {
            if (mealListDB.get(i).getId() == id)
                meal = mealListDB.get(i);
            i++;
        }
        mealListDB.remove(meal);
    }


    @Override
    public Meal getOneById(int id) {
        Meal meal = null;
        for ( Meal m : mealListDB) {
            if (m.getId() == id) meal = m;
        }
        return meal;
    }
}
