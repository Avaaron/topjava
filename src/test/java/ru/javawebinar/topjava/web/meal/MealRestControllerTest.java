package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.contentJson;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected MealService mealService;

    private static final String REST_URL = MealRestController.REST_URI + '/';

    @Test
    void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void createMeal() throws Exception {
        Meal created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        Meal returned = TestUtil.readFromJson(action, Meal.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void update() throws Exception {
        Meal meal = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), meal);
    }

    @Test
    void getBetweenTest() throws Exception {
        mockMvc.perform(get(REST_URL + REST_FILTER_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(MealTestData.MEAL6_WE, MealTestData.MEAL5_WE,
                        MealTestData.MEAL3_WE, MealTestData.MEAL2_WE)));
    }

    @Test
    void getBetweenWithEmptyTest() throws Exception {
        mockMvc.perform(get(REST_URL + REST_FILTER_URL_WITH_EMPTY_PARAM))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(MealTestData.MEAL6_WE, MealTestData.MEAL5_WE, MealTestData.MEAL4_WE,
                        MealTestData.MEAL3_WE, MealTestData.MEAL2_WE, MealTestData.MEAL1_WE)));
    }

    @Test
    void getBetweenNewTest() throws Exception {
        mockMvc.perform(get(REST_URL + REST_BETWEEN_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(MealTestData.MEAL6_WE, MealTestData.MEAL5_WE,
                        MealTestData.MEAL3_WE, MealTestData.MEAL2_WE)));
    }
}