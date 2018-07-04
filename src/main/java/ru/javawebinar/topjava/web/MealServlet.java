package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDaoImpl;



    @Override
    public void init() throws ServletException {
        this.mealDaoImpl = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("act");
        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                mealDaoImpl.delete(id);
                response.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal unit = mealDaoImpl.getOneById(id);
                request.setAttribute("unit", unit);
                List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDaoImpl.getAll(), LocalTime.MIN,
                        LocalTime.MAX, 2000);
                request.setAttribute("mealWithExceeds", mealWithExceeds);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
            }
        }else {
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDaoImpl.getAll(), LocalTime.MIN,
                    LocalTime.MAX, 2000);
            request.setAttribute("mealWithExceeds", mealWithExceeds);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals after create");
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        String dateTimeStr = req.getParameter("dateTime");
        String message = new String();
        int calories = Integer.parseInt(req.getParameter("calories"));
        if(req.getParameter("mealId") == null || req.getParameter("mealId").isEmpty()) {
            if(dateTimeStr.isEmpty() || dateTimeStr == null) {
                mealDaoImpl.create(new Meal(LocalDateTime.now(), description, calories));
            } else {
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                    mealDaoImpl.create(new Meal(dateTime, description, calories));
                } catch (Exception e){
                    message = "Неверный формат даты!!! Дату и время необходимо ввести в формате: dd.MM.yyyy HH:mm";
                }
            }
        }else {
            int id = Integer.parseInt(req.getParameter("mealId"));
            LocalDateTime dateTime = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                Meal mealForUpdate = new Meal(dateTime, description, calories);
                mealForUpdate.setId(id);
                mealDaoImpl.update(mealForUpdate);
            } catch (Exception e) {
                message = "Неверный формат даты!!! Дату и время необходимо ввести в формате: dd.MM.yyyy HH:mm";
            }

        }
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDaoImpl.getAll(), LocalTime.MIN,
                LocalTime.MAX, 2000);
        req.setAttribute("message", message);
        req.setAttribute("mealWithExceeds", mealWithExceeds);
        resp.sendRedirect("meals");

    }
}
