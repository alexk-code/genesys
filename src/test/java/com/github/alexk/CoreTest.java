package com.github.alexk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreTest {
    protected WebDriver driver;
    protected static final Logger LOGGER = LoggerFactory.getLogger(CoreTest.class);

    @BeforeEach
    public void setUp() { 
        driver = WebDriverFactory.createDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
