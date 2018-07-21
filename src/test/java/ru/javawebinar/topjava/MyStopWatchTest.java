package ru.javawebinar.topjava;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyStopWatchTest extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public static Map<String, Long> logs  = new HashMap<>();

    private static void logInfo(Description description, long nanos) {
        String testName = description.getMethodName();
        logs.put(description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        log.info("TEST {}: {} мс", testName, TimeUnit.NANOSECONDS.toMillis(nanos));;
    }

        @Override
        protected void finished(long nanos, Description description) {

            logInfo(description, nanos);
        }

    }

