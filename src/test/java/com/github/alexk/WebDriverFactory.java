package com.github.alexk;

import org.openqa.selenium.PageLoadStrategy;
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
        boolean isPerformanceProfile = ConfigReader.getPerformanceProfile();
        PageLoadStrategy pageLoadStrategy = ConfigReader.getPageLoadStrategy();
        ChromeOptions chromeOptions = new ChromeOptions();

        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            LOGGER.info("Headless mode mode of '{}' browser is active", BrowserType.CHROME);
        }

        if (isPerformanceProfile) {
            chromeOptions.addArguments("--disable-background-timer-throttling");
            chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
            chromeOptions.addArguments("--disable-renderer-backgrounding");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            LOGGER.info("Performance Profile mode of '{}' browser is active", BrowserType.CHROME);
        }

        chromeOptions.setPageLoadStrategy(pageLoadStrategy);
        LOGGER.info("Page Load Strategy of '{}' browser is set to '{}'", BrowserType.CHROME, pageLoadStrategy.name());

        return new ChromeDriver(chromeOptions);
    }

    private static WebDriver createFirefoxDriver() {
        boolean isHeadless = ConfigReader.getHeadless();
        boolean isPerformanceProfile = ConfigReader.getPerformanceProfile();
        PageLoadStrategy pageLoadStrategy = ConfigReader.getPageLoadStrategy();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (isHeadless) {
            firefoxOptions.addArguments("--headless");
            LOGGER.info("Headless mode mode of '{}' browser is active", BrowserType.FIREFOX);
        }

        if (isPerformanceProfile) {
            firefoxOptions.addPreference("dom.max_script_run_time", 0);
            firefoxOptions.addPreference("browser.cache.disk.enable", false);
            firefoxOptions.addPreference("browser.cache.memory.enable", false);
            firefoxOptions.addPreference("browser.tabs.remote.autostart", false);
            LOGGER.info("Performance Profile mode of '{}' browser is active", BrowserType.FIREFOX);
        }

        firefoxOptions.setPageLoadStrategy(pageLoadStrategy);
        LOGGER.info("Page Load Strategy of '{}' browser is set to '{}'", BrowserType.CHROME, pageLoadStrategy.name());

        return new FirefoxDriver(firefoxOptions);
    }

    private static WebDriver createEdgeDriver() {
        boolean isHeadless = ConfigReader.getHeadless();
        boolean isPerformanceProfile = ConfigReader.getPerformanceProfile();
        PageLoadStrategy pageLoadStrategy = ConfigReader.getPageLoadStrategy();
        EdgeOptions edgeOptions = new EdgeOptions();

        if (isHeadless) {
            edgeOptions.addArguments("--headless");
            LOGGER.info("Headless mode mode of '{}' browser is active", BrowserType.EDGE);
        }

        if (isPerformanceProfile) {
            edgeOptions.addArguments("--disable-background-timer-throttling");
            edgeOptions.addArguments("--disable-backgrounding-occluded-windows");
            edgeOptions.addArguments("--disable-renderer-backgrounding");
            edgeOptions.addArguments("--disable-extensions");
            edgeOptions.addArguments("--no-sandbox");
            edgeOptions.addArguments("--disable-dev-shm-usage");
            LOGGER.info("Performance Profile mode of '{}' browser is active", BrowserType.EDGE);
        }

        edgeOptions.setPageLoadStrategy(pageLoadStrategy);
        LOGGER.info("Page Load Strategy of '{}' browser is set to '{}'", BrowserType.CHROME, pageLoadStrategy.name());

        return new EdgeDriver(edgeOptions);
    }
}
