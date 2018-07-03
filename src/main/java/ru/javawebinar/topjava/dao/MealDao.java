package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;

public interface MealDao {

    void create(LocalDateTime dateTime, String description, int calories);
    void update(int id, LocalDateTime dateTime, String Description, int calories);
    void delete(int id);
    Meal getOneById(int id);

}
