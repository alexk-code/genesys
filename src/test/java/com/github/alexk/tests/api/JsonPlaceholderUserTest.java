package com.github.alexk.tests.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.apis.jsonplaceholder.JsonPlaceholderUserService;
import com.github.alexk.utils.ConfigReader;

public class JsonPlaceholderUserTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPlaceholderUserTest.class);
    private JsonPlaceholderUserService userService;

    @BeforeEach
    public void setup() {
        String baseUrl = ConfigReader.getProperty("jsonplaceholder_baseurl");
        userService = new JsonPlaceholderUserService(baseUrl);
    }

    @Test
    public void testGetUsersAndVerifyFirstEmail() throws IOException {
        List<String> emails = userService.getUserEmails();

        LOGGER.info("User Emails: \r{}", String.join("\n", emails));

        assertFalse(emails.isEmpty(), "Email list is empty");
        String firstEmail = emails.get(0);
        LOGGER.info("Checking if the first email contains '@'");
        assertTrue(firstEmail.contains("@"), "The email does not contain '@'");
    }
}
