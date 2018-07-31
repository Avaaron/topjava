package ru.javawebinar.topjava.service.datajpa.test;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJPAUserServiceTest extends UserServiceTest {
}
