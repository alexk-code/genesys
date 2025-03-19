package com.github.alexk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

public class WebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }

    private static BrowserType getBrowserTypeFromConfig() {
        String browser = ConfigReader.getProperty("browser");
        BrowserType browserType;

        try {
            browserType = BrowserType.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unsupported browser type: '{}', defaulting to {}", browser, BrowserType.CHROME);
            return BrowserType.CHROME;
        }

        return browserType;
    }

    public static WebDriver createDriver() {
        BrowserType browserType = getBrowserTypeFromConfig();
        WebDriver driver = null;

        LOGGER.info("Creating WebDriver for browser: {}", browserType);

        switch (browserType) {
            case CHROME:
                driver = createChromeDriver();
                break;
            case FIREFOX:
                driver = createFirefoxDriver();
                break;
            case EDGE:
                driver = createEdgeDriver();
                break;
            default:
                LOGGER.error("No WebDriver implemented for browser: {}", browserType);
                throw new UnsupportedOperationException("Browser type " + browserType + " not supported.");
        }

        return driver;
    }

    private static WebDriver createChromeDriver() {
        boolean isHeadless = ConfigReader.getHeadless();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            LOGGER.info("Running {} browser in headless mode", BrowserType.CHROME);
        }
        return new ChromeDriver(chromeOptions);
    }
    
    private static WebDriver createFirefoxDriver() {
        boolean isHeadless = ConfigReader.getHeadless();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (isHeadless) {
            firefoxOptions.addArguments("--headless");
            LOGGER.info("Running {} browser in headless mode", BrowserType.FIREFOX);
        }
        return new FirefoxDriver(firefoxOptions);
    }
    
    private static WebDriver createEdgeDriver() {
        boolean isHeadless = ConfigReader.getHeadless();
        EdgeOptions edgeOptions = new EdgeOptions();
        if (isHeadless) {
            edgeOptions.addArguments("--headless");
            LOGGER.info("Running {} browser in headless mode", BrowserType.EDGE);
        }
        return new EdgeDriver(edgeOptions);
    }
}
