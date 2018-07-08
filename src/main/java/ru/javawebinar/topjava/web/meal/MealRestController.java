package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll(int userId){
        log.info("getAll");
        return (List<Meal>) service.getAll(userId);
    }

    public List<MealWithExceed>getFiltred(int userId, String startDateStr, String startTimeStr, String endDateStr, String endTimeStr){
        log.info("getFiltred");
        LocalDate startLocalDate;
        LocalTime startLocalTime;
        LocalDate endLocalDate;
        LocalTime endLocalTime;

        List<MealWithExceed> mealWithExceeds;
        if (startDateStr == null) {
            mealWithExceeds = MealsUtil.getWithExceeded(getAll(userId), LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
        } else {
            if (startDateStr.isEmpty()) startLocalDate = LocalDate.MIN;
            else startLocalDate = LocalDate.parse(startDateStr);
            if (endDateStr.isEmpty()) endLocalDate = LocalDate.MAX;
            else endLocalDate = LocalDate.parse(endDateStr);
            if (startTimeStr.isEmpty()) startLocalTime = LocalTime.MIN;
            else startLocalTime = LocalTime.parse(startTimeStr);
            if (endTimeStr.isEmpty()) endLocalTime = LocalTime.MAX;
            else endLocalTime = LocalTime.parse(endTimeStr);
            mealWithExceeds = MealsUtil.getWithExceeded(getAll(userId), startLocalTime, endLocalTime, SecurityUtil.authUserCaloriesPerDay()).stream()
                    .filter(mealWithExceed -> DateTimeUtil.isBetweenDate(mealWithExceed.getDateTime().toLocalDate(), startLocalDate, endLocalDate))
                    .collect(Collectors.toList());
            }
        return mealWithExceeds;
    }

    public Meal get(int id, int userId){
        log.info("get");
        return  service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

}