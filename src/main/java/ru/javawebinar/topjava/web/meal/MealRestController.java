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
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        return MealsUtil.getWithExceeded((List<Meal>) service.getAll(SecurityUtil.authUserId()),  LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltred(String startDateStr, String startTimeStr, String endDateStr, String endTimeStr) {
        log.info("getFiltred");
        LocalDate startLocalDate;
        LocalTime startLocalTime;
        LocalDate endLocalDate;
        LocalTime endLocalTime;
        if (startDateStr.isEmpty()) startLocalDate = LocalDate.MIN;
        else startLocalDate = LocalDate.parse(startDateStr);
        if (endDateStr.isEmpty()) endLocalDate = LocalDate.MAX;
        else endLocalDate = LocalDate.parse(endDateStr);
        if (startTimeStr.isEmpty()) startLocalTime = LocalTime.MIN;
        else startLocalTime = LocalTime.parse(startTimeStr);
        if (endTimeStr.isEmpty()) endLocalTime = LocalTime.MAX;
        else endLocalTime = LocalTime.parse(endTimeStr);
        List<Meal> byTime = (List<Meal>) service.getRepositotyFilter(SecurityUtil.authUserId(), startLocalDate, endLocalDate, startLocalTime, endLocalTime);
        return MealsUtil.getWithExceeded(byTime, LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());

    }

    public Meal get(int id){
        log.info("get");
        return  service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, SecurityUtil.authUserId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, SecurityUtil.authUserId());
    }

}