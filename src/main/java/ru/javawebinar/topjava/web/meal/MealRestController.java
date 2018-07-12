package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public List<MealWithExceed> getFiltred(String startDateStr, String startTimeStr, String endDateStr, String endTimeStr) {
        log.info("getFiltred");
        LocalDate startLocalDate = startDateStr.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDateStr);
        LocalDate endLocalDate = endDateStr.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDateStr);
        LocalTime startLocalTime = startTimeStr.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTimeStr);
        LocalTime endLocalTime = endTimeStr.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTimeStr);
        return service.getRepositotyFilter(SecurityUtil.authUserId(), startLocalDate, endLocalDate, startLocalTime, endLocalTime);

    }

    public Meal get(int id){
        log.info("get");
        return  service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        service.update(meal);
    }

}