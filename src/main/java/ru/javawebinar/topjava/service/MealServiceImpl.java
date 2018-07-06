package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository){
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal){
       return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException{
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    @Override
    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    @Override
    public List<MealWithExceed> getAll(int userId){
        List<Meal> list = new ArrayList<>(repository.getAll(userId));
        return MealsUtil.getWithExceeded(list, LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}