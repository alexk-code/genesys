package com.github.alexk.utils;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties = new Properties();
    private static final String PROPERTIES_FILE_PATH = "src/test/resources/config.properties";
    private static final String JSON_FILE_PATH = "src/test/resources/credentials.json";
    private static final String DEFAULT_PAGE_LOAD_STRATEGY = "NORMAL";
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;

    static {
        loadProperties();
        loadJsonProperties();
    }

    private static void loadProperties() {
        try (FileInputStream file = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(file);
        } catch (IOException e) {
            LOGGER.error("Could not load config.properties file", e);
            throw new RuntimeException("Could not load config.properties file", e);
        }
    }

    private static void loadJsonProperties() {
        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Iterate over user-level keys
            for (Object key : jsonObject.keySet()) {
                String userKey = (String) key;
                JSONObject userObj = (JSONObject) jsonObject.get(userKey);
                // Iterate over credential-level keys
                for (Object credKey : userObj.keySet()) {
                    String compositeKey = userKey + "." + credKey;
                    String cred = (String) userObj.get(credKey);
                    properties.setProperty(compositeKey, cred);
                }
            }
            LOGGER.debug("Successfully loaded JSON properties from " + JSON_FILE_PATH);
        } catch (IOException | ParseException e) {
            LOGGER.error("Could not load credentials.json file", e);
            throw new RuntimeException("Could not load credentials.json file", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warn("Property key '{}' is missing in the configuration", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        if (value == null) {
            LOGGER.warn("Property key '{}' is missing in the configuration, defaulting to '{}'", key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    public static String getBaseUrl() {
        return getProperty("base_url");
    }

    public static String getExtendedBaseUrl(String extendedPath) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Base URL is not configured properly");
        }

        try {
            URI baseUri = new URI(baseUrl);
            URI resolvedUri = baseUri.resolve(extendedPath);
            return resolvedUri.toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid base URL: " + baseUrl, e);
        }
    }

    public static boolean getHeadless() {
        String headless = getProperty("headless", "false");

        if (!"true".equalsIgnoreCase(headless) && !"false".equalsIgnoreCase(headless)) {
            LOGGER.warn("Invalid value for 'headless': '{}', set to default: 'false'", headless);
        }

        return Boolean.parseBoolean(headless);
    }

    public static boolean getPerformanceProfile() {
        String performanceProfile = getProperty("performance_profile", "false");

        if (!"true".equalsIgnoreCase(performanceProfile) && !"false".equalsIgnoreCase(performanceProfile)) {
            LOGGER.warn("Invalid value for 'performance_profile': '{}', set to default: 'false'", performanceProfile);
            return false;
        }

        return Boolean.parseBoolean(performanceProfile);
    }

    public static PageLoadStrategy getPageLoadStrategy() {
        String pageLoadStrategy = getProperty("page_load_strategy", DEFAULT_PAGE_LOAD_STRATEGY);

        if (!"normal".equalsIgnoreCase(pageLoadStrategy) && !"eager".equalsIgnoreCase(pageLoadStrategy)
                && !"none".equalsIgnoreCase(pageLoadStrategy)) {
            LOGGER.warn("Invalid value for 'page_load_strategy': '{}', set to default: '{}'", pageLoadStrategy, DEFAULT_PAGE_LOAD_STRATEGY);
            return PageLoadStrategy.NORMAL;
        }

        switch (pageLoadStrategy.toUpperCase()) {
            case "NORMAL":
                return PageLoadStrategy.NORMAL;
            case "EAGER":
                return PageLoadStrategy.EAGER;
            case "NONE":
                return PageLoadStrategy.NONE;
            default:
                return PageLoadStrategy.NORMAL;
        }
    }

    public static Duration getWebDriverWaitTimeout() {
        String timeout = properties.getProperty("webdriver_wait_timeout", String.valueOf(DEFAULT_TIMEOUT));

        try {
            return Duration.ofSeconds(Long.parseLong(timeout));
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid 'webdriver_wait_timeout' value: '{}', set to default: '{}' seconds", timeout, DEFAULT_TIMEOUT);
            return Duration.ofSeconds(10);
        }
    }

    public static Dimension getWindowDimension() {
        String width = properties.getProperty("window_width", String.valueOf(DEFAULT_WIDTH));
        String height = properties.getProperty("window_height", String.valueOf(DEFAULT_HEIGHT));

        try {
            int w = Integer.parseInt(width);
            int h = Integer.parseInt(height);
            return new Dimension(w, h);
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid 'width' and/or 'height' values: '{}x{}', set to default: '{}x{}'", width, height,
                    DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }

    /*
     * TODO: Do a spike. Once a ChromeDriver is initialized, you cannot modify its
     * ChromeOptions directly, because the options are used at the time of
     * initialization to configure the driver.
     * Useing Chrome DevTools Protocol (CDP) for runtime changes is possible:
     * 
     * ChromeDevTools devTools = ((ChromeDriver) driver).getDevTools();
     * devTools.createSession();
     * 
     * devTools.send(Network.enable());
     * devTools.send(Network.setCacheDisabled(true));
     */
    public static void setHeadless(boolean isHeadless) {
        String headless = isHeadless ? "true" : "false";

        properties.setProperty("headless", headless);

        LOGGER.info("'headless' property set to: '{}'", headless);
    }

    public static void setPerformanceProfile(boolean isPerformanceProfile) {
        String performanceProfile = isPerformanceProfile ? "true" : "false";

        properties.setProperty("headless", performanceProfile);

        LOGGER.info("'headless' property set to: '{}'", performanceProfile);
    }

    public static void setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
        String strategy = pageLoadStrategy.name();

        properties.setProperty("page_load_strategy", strategy);

        LOGGER.info("'page_load_strategy' set to: '{}'", strategy);
    }
}