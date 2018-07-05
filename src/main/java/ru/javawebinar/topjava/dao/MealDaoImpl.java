package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;



public class MealDaoImpl implements MealDao{
    private Map<Integer, Meal> mealMapDB = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(6);

    public MealDaoImpl(){
        for (Meal meal : MealsUtil.meals){
            this.mealMapDB.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new CopyOnWriteArrayList<>(mealMapDB.values());
    }

    @Override
    public Meal create(Meal meal) {
        Meal item = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        int id = idCounter.incrementAndGet();
        item.setId(id);
        mealMapDB.put(id, item);
        return item;
    }

    @Override
    public Meal update(Meal meal) {
        mealMapDB.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealMapDB.remove(id);
    }


    @Override
    public Meal getOneById(int id) {
        return mealMapDB.get(id);
    }
}
