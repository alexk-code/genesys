package com.github.alexk;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        Duration timeout = ConfigReader.getWebDriverWaitTimeout();
        this.wait = new WebDriverWait(driver, timeout);
        LOGGER.info("'WebDriverWait' is set to '{} seconds' ", timeout);
    }
}
