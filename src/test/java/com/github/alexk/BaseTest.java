package com.github.alexk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

public class BaseTest {
    protected WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() { 
        driver = WebDriverFactory.createDriver();
        Dimension dimension = ConfigReader.getWindowDimension();
        driver.manage().window().setSize(dimension);
        LOGGER.info("Window size is set to '{}x{}'", dimension.getWidth(), dimension.getHeight());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
