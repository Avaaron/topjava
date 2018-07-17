package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-jdbc.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getBetweenDates() {
        List<Meal> list = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 31),MealTestData.AUHT_USER);
        MealTestData.assertMatch(list, MealTestData.MEAL3, MealTestData.MEAL2, MealTestData.MEAL1);
    }

    @Test
    public void getBetweenDatesWrongUserId() {
        List<Meal> list = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 31), MealTestData.WRONG_USER);
        MealTestData.assertMatch(list, new ArrayList<>());
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> list = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 9, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0), MealTestData.AUHT_USER);
        MealTestData.assertMatch(list, MealTestData.MEAL2, MealTestData.MEAL1);
    }

    @Test
    public void getBetweenDateTimesWrongUserId() {
        List<Meal> list = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 9, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0), MealTestData.WRONG_USER);
        MealTestData.assertMatch(list, new ArrayList<>());
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUserId() {
        service.get(100006, MealTestData.WRONG_USER);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongId() {
        service.get(100009, MealTestData.AUHT_USER );
    }

    @Test
    public void get() {
       Meal meal = service.get(100003, MealTestData.AUHT_USER );
       MealTestData.assertMatch(meal, MealTestData.MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUserId() {
        service.delete(100006, MealTestData.WRONG_USER);
    }



    @Test(expected = NotFoundException.class)
    public void deleteWrongId() {
        service.delete(100009, MealTestData.AUHT_USER);
    }

    @Test
    public void delete() {
        service.delete(100004, MealTestData.AUHT_USER);
        List<Meal> list = service.getAll(MealTestData.AUHT_USER);
        MealTestData.assertMatch(list, MealTestData.MEAL2, MealTestData.MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> list = service.getAll(MealTestData.AUHT_USER);
        MealTestData.assertMatch(list, MealTestData.MEAL3, MealTestData.MEAL2, MealTestData.MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUserId() {
        Meal upd = MealTestData.MEAL6;
        upd.setCalories(300);
        upd.setDescription("upd");
        service.update(upd, MealTestData.WRONG_USER);

    }

    @Test
    public void update() {
        Meal upd = new Meal(MealTestData.MEAL1.getId(), MealTestData.MEAL1.getDateTime(), MealTestData.MEAL1.getDescription(),
                MealTestData.MEAL1.getCalories());
        upd.setCalories(300);
        service.update(upd, MealTestData.AUHT_USER);
        MealTestData.assertMatch(service.get(100002, MealTestData.AUHT_USER), upd);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "завтрак", 500);
        Meal createdMeal = service.create(newMeal, MealTestData.AUHT_USER);
        MealTestData.assertMatch(new Meal(100008, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "завтрак", 500), createdMeal);
    }

}