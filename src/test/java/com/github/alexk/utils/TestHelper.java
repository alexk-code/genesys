package com.github.alexk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestHelper.class);

    public static void testInfo(boolean isStart, String testName) {
        String suffix = isStart ? "is started" : "is finished";
        String stars = "=".repeat(testName.length() + suffix.length() + 3);
        LOGGER.info(stars);
        LOGGER.info(" {} {} ", testName, suffix);
        LOGGER.info(stars);
    }
}
