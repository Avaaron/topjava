package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;
import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JPAUserServiceTest extends UserServiceTest {
}
