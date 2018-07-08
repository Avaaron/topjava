package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(6);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal get(int id) {
        if(!repository.isEmpty()) {
            return repository.get(id);
        }
        else return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if(!repository.values().stream()
                .filter(meal -> (meal.getId() == id))
                .collect(Collectors.toList())
                .isEmpty()) {
            repository.remove(id);
            return true;
        }
        else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id, userId);
        if(!repository.containsKey(id) || userId != repository.get(id).getUserId()) return null;
        else return repository.get(id);

    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .filter(meal -> meal.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}

