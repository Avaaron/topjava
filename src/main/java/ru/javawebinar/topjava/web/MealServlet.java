package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext cappCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController mealRestController;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRestController = cappCtx.getBean(MealRestController.class);

    }

    @Override
    public void destroy() {
        super.destroy();
        cappCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        LocalDateTime localDateTime =LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        log.info(id.isEmpty() ? "Create {}" : "Update {}");
        Meal meal = new Meal(null,localDateTime.truncatedTo(ChronoUnit.MINUTES), description, calories);
        if (id.isEmpty() || id == null) {
            mealRestController.create(meal);
        } else {
            meal.setId(Integer.valueOf(id));
            mealRestController.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null || action.equals("timeFilter") ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                String mealId = request.getParameter("id");
                Meal meal;
                if (mealId == null ||  mealId.isEmpty()) {
                    meal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                }else meal = mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String startDateStr = request.getParameter("startDate");
                String startTimeStr = request.getParameter("startTime");
                String endDateStr = request.getParameter("endDate");
                String endTimeStr = request.getParameter("endTime");
                String clean = request.getParameter("clean");
                if (clean != null) {
                    response.sendRedirect("meals");
                }
                else if(startDateStr == null) {
                    request.setAttribute("meals", mealRestController.getAll());
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
                else {
                    request.setAttribute("startDate", startDateStr);
                    request.setAttribute("endDate", endDateStr);
                    request.setAttribute("startTime", startTimeStr);
                    request.setAttribute("endTime", endTimeStr);
                    request.setAttribute("meals", mealRestController.getFiltred(startDateStr, startTimeStr, endDateStr, endTimeStr));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }


                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}