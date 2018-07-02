package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao{

    private static AtomicInteger idCounter = new AtomicInteger(6);

    @Override
    public void create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(idCounter.incrementAndGet());
        mealListDB.add(meal);
    }

    @Override
    public void update(int id, String description, int calories) {
        for (Meal meal : mealListDB) {
            if (meal.getId() == id){
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
    public void read(int id) {

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
