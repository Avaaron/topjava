package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;

    public MealServlet() {
        super();
        this.mealDao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("act");
        if (action != null){
            if (action.equalsIgnoreCase("delete")){
                int id = Integer.parseInt(request.getParameter("id"));
                mealDao.delete(id);
            } else if (action.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal unit = mealDao.getOneById(id);
                request.setAttribute("unitDescription", unit.getDescription());
                request.setAttribute("unitId", unit.getId() );
                request.setAttribute("unitCalories", unit.getCalories());
            }
        }
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDao.mealListDB, LocalTime.MIN,
                    LocalTime.MAX, 2000);
        request.setAttribute("mealWithExceeds", mealWithExceeds);
        request.getRequestDispatcher("meals.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals after create");
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        if(req.getParameter("mealId") == null || req.getParameter("mealId").isEmpty()) {
            mealDao.create(LocalDateTime.now(), description, calories);
        }else {
            int id = Integer.parseInt(req.getParameter("mealId"));
            mealDao.update(id, description, calories);
        }
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDao.mealListDB, LocalTime.MIN,
                LocalTime.MAX, 2000);

        req.setAttribute("mealWithExceeds", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
