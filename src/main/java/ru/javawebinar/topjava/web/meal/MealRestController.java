package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(),  LocalTime.MIN, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltred(String startDateStr, String startTimeStr, String endDateStr, String endTimeStr) {
        log.info("getFiltred");
        LocalDate startLocalDate;
        LocalTime startLocalTime;
        LocalDate endLocalDate;
        LocalTime endLocalTime;
        startLocalDate = startDateStr.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDateStr);
        endLocalDate = endDateStr.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDateStr);
        startLocalTime = startTimeStr.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTimeStr);
        endLocalTime = endTimeStr.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTimeStr);
        return service.getRepositotyFilter(startLocalDate, endLocalDate, startLocalTime, endLocalTime);

    }

    public Meal get(int id){
        log.info("get");
        return  service.get(id, SecurityUtil.authUserId());
    }

    public Meal createDefaultMeal() {
        return new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
    }

    public Meal create(LocalDateTime localDateTime, String description, int calories) {
        Meal meal = new Meal(SecurityUtil.authUserId(), localDateTime.truncatedTo(ChronoUnit.MINUTES), description, calories);
        log.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal);
        service.update(meal, SecurityUtil.authUserId());
    }

}