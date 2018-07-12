package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public void delete(int id, int userId) throws NotFoundException{
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<MealWithExceed> getRepositotyFilter(int userId, LocalDate startLocalDate, LocalDate endLocalDate, LocalTime startLocalTime, LocalTime endLocalTime) {
        return MealsUtil.getWithExceeded(repository.filter(userId,startLocalDate, endLocalDate), startLocalTime, endLocalTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), repository.get(id, userId).getId());
    }

    @Override
    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    @Override
    public List<MealWithExceed> getAll(int userId){
        return MealsUtil.getWithExceeded(repository.getAll(userId), LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
    }

}