package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;
    private int authUserId = SecurityUtil.authUserId();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(authUserId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id)  {
        mealService.delete(id, authUserId);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String update (@PathVariable("id") int id, Model model) {
        Meal meal = mealService.get(id, authUserId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }
    @GetMapping("/create")
    public String create (Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "mealForm";
    }

    @PostMapping
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF8");
        String id = request.getParameter("id");
        Meal userMeal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (userMeal.isNew()) {
            mealService.create(userMeal, authUserId);
        } else {
            mealService.update(userMeal, authUserId);
        }
        return "redirect:meals";
    }

}
