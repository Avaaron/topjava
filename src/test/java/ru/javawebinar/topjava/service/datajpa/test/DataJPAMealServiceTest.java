package ru.javawebinar.topjava.service.datajpa.test;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJPAMealServiceTest extends MealServiceTest {
}
