package com.github.alexk.utils;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties = new Properties();
    private static final String PROPERTIES_FILE_PATH = "src/test/resources/config.properties";
    private static final String JSON_FILE_PATH = "src/test/resources/credentials.json";

    static {
        loadProperties();
        loadJsonProperties();
    }

    private static void loadProperties() {
        try (FileInputStream file = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(file);
        } catch (IOException e) {
            LOGGER.error("Could not load config.properties file!", e);
            throw new RuntimeException("Could not load config.properties file!", e);
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
            LOGGER.info("Successfully loaded JSON properties from " + JSON_FILE_PATH);
        } catch (IOException | ParseException e) {
            LOGGER.error("Could not load credentials.json file!", e);
            throw new RuntimeException("Could not load credentials.json file!", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warn("Property key '{}' is missing in the configuration.", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        if (value == null) {
            LOGGER.warn("Property key '{}' is missing in the configuration, defaulting to '{}'.", key, defaultValue);
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
            throw new IllegalArgumentException("Base URL is not configured properly.");
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
        boolean isHeadless = Boolean.parseBoolean(headless);
        if (!headless.equalsIgnoreCase("true") && !headless.equalsIgnoreCase("false")) {
            LOGGER.warn("Invalid value for '{}', defaulting to false.");
        }
        return isHeadless;
    }
}