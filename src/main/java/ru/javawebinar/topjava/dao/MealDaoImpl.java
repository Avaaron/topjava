package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;



public class MealDaoImpl implements MealDao{
    private Map<Integer, Meal> mealMapDB = new ConcurrentSkipListMap<>();
    private AtomicInteger idCounter = new AtomicInteger(6);

    public MealDaoImpl(){
        for (Meal meal : MealsUtil.meals){
            this.mealMapDB.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealsList = new ArrayList<>();
        for (Map.Entry<Integer, Meal> pair : mealMapDB.entrySet()){
            mealsList.add(pair.getValue());
        }
        return mealsList;
    }

    @Override
    public Meal create(Meal meal) {
        Meal item = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealMapDB.put(idCounter.incrementAndGet(), item);
        return item;
    }

    @Override
    public Meal update(Meal meal) {
        Meal item = null;
        item = mealMapDB.get(meal.getId());
        item.setDateTime(meal.getDateTime());
        item.setDescription(meal.getDescription());
        item.setCalories(meal.getCalories());
        mealMapDB.put(meal.getId(), item);
        return item;
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
