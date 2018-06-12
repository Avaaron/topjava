package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );


        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    private static LocalDate toLocalDate(UserMeal userMeal){
        LocalDate localDate = userMeal.getDateTime().toLocalDate();
        return localDate;
    }

    private static LocalTime toLocalTime(UserMeal userMeal){
        LocalTime localTime = userMeal.getDateTime().toLocalTime();
        return localTime;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {
        mealList.sort((o1, o2) -> {
            if (toLocalDate(o1).isEqual(toLocalDate(o2)))return 0;
            else if(toLocalDate(o1).isAfter(toLocalDate(o2))) return 1;
            else return -1;
        });
        List<UserMealWithExceed> userExceed = new ArrayList<>();
        int calloriesTotal = 0;
        int beginDataChange = 0;
        LocalDate date = toLocalDate(mealList.get(0));
        for(int i = 0; i < mealList.size(); i++){

            if(toLocalDate(mealList.get(i)).isEqual(date) ){
                calloriesTotal += mealList.get(i).getCalories();
                if(i == mealList.size() - 1){
                    for(int j = beginDataChange; j < i; j++) {
                        if (TimeUtil.isBetween(toLocalTime(mealList.get(j)), startTime, endTime)) {
                            userExceed.add(new UserMealWithExceed(mealList.get(j).getDateTime(), mealList.get(j).getDescription(),
                                    mealList.get(j).getCalories(), calloriesTotal > caloriesPerDay));
                        }
                    }
                }
            }else{
                for(int j = beginDataChange; j < i; j++){
                    if(TimeUtil.isBetween(toLocalTime(mealList.get(j)), startTime, endTime)){
                        userExceed.add(new UserMealWithExceed(mealList.get(j).getDateTime(), mealList.get(j).getDescription(),
                                mealList.get(j).getCalories(), calloriesTotal > caloriesPerDay));
                    }
                }

                calloriesTotal = 0;
                date = toLocalDate(mealList.get(i));
                beginDataChange = i;
                i--;
            }
        }



        return userExceed;
    }
}
