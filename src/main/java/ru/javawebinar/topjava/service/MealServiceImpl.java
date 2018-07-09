package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
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
    public Collection<Meal> getRepositotyFilter(int userId, LocalDate startLocalDate, LocalDate endLocalDate, LocalTime startLocalTime, LocalTime endLocalTime) {
        return repository.filter(userId, startLocalDate, endLocalDate, startLocalTime, endLocalTime);
    }

    @Override
    public Meal get(int id, int userId) {
        checkNotFoundWithId(repository.get(id, userId), repository.get(id, userId).getId());
        return checkNotFoundWithId(repository.get(id, userId), repository.get(id, userId).getId());
    }

    @Override
    public void update(Meal meal, int userId) {
        if(meal.getUserId() == userId) checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    @Override
    public List<Meal> getAll(int userId){
        return repository.getAll(userId);
    }

}