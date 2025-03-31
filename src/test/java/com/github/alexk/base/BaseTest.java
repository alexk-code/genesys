package com.github.alexk.base;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;
import com.github.alexk.utils.WebDriverFactory;

public class BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    @BeforeEach
    public void setUp() { 
        WebDriver driver = WebDriverFactory.createDriver();
        driverThreadLocal.set(driver);
        Dimension dimension = ConfigReader.getWindowDimension();
        driver.manage().window().setSize(dimension);

        LOGGER.debug("Window size is set to '{}x{}'", dimension.getWidth(), dimension.getHeight());

        Duration timeout = ConfigReader.getWebDriverWaitTimeout();
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        waitThreadLocal.set(wait);

        LOGGER.debug("'WebDriverWait' is set to '{} seconds' ", timeout);
    }

    @AfterEach
    public void tearDown() {
        WebDriver driver = getDriver();

        if (driver != null) {
            driver.quit();
        }

        driverThreadLocal.remove();
        waitThreadLocal.remove();
    }
}
