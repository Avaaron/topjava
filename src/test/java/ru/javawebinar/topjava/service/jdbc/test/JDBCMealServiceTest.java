package ru.javawebinar.topjava.service.jdbc.test;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JDBCMealServiceTest extends MealServiceTest {
}
