package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.persistence.Converter;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.MAX_DATE_TIME;
import static ru.javawebinar.topjava.util.DateTimeUtil.MIN_DATE_TIME;

@RestController
@RequestMapping(value = MealRestController.REST_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static  final String REST_URI = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal created = super.create(meal);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(uriOfNewResource);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URI + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @GetMapping(value = "/filter")
    public List<MealWithExceed> getBetween(@RequestParam(value = "startDateTime")  LocalDateTime startDateTime,
                                           @RequestParam(value = "endDateTime")    LocalDateTime endDateTime) {
        startDateTime = (startDateTime == null) ? MIN_DATE_TIME : startDateTime;
        endDateTime = (endDateTime == null) ? MAX_DATE_TIME : endDateTime;
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }

    @RequestMapping(value = "/between", method = RequestMethod.GET)
    public List<MealWithExceed> getBetweenNew(@RequestParam(value = "startDate", required = false)  LocalDate startDate,
                                              @RequestParam(value = "startTime", required = false)  LocalTime startTime,
                                              @RequestParam(value = "endDate", required = false)    LocalDate endDate,
                                              @RequestParam(value = "endTime", required = false)    LocalTime endTime) {

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}