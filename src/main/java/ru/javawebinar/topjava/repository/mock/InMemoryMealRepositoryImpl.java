package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal MEAL : MealsUtil.MEALS) {
            MEAL.setId(counter.incrementAndGet());
            repository.put(MEAL.getId(), MEAL);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {

            log.info("save {}", meal);
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            } else if (meal.getUserId() == userId) {
                // treat case: update, but absent in storage
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            }
            else return null;

    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Meal meal = get(id, userId);
        if (meal != null && meal.getUserId() == userId) {
            repository.remove(id);
            return true;
        }
        else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id, userId);
        if(repository.containsKey(id) && userId == repository.get(id).getUserId()) return repository.get(id);
        else return null;

    }

    @Override
    public List<Meal> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> filter(LocalDate startLocalDate, LocalDate endLocalDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == SecurityUtil.authUserId() && DateTimeUtil.isBetweenDate(meal.getDate(), startLocalDate, endLocalDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

