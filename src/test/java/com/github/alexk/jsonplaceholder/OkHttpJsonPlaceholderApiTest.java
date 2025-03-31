package com.github.alexk.jsonplaceholder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Disabled
public class OkHttpJsonPlaceholderApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpJsonPlaceholderApiTest.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUsersAndVerifyFirstEmail() {
        String url = ConfigReader.getProperty("jsonplaceholder_baseurl");
        String path = "/users";

        Request request = new Request.Builder()
                .url(url + path)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.isSuccessful(), "HTTP request failed with code: " + response.code());

            ResponseBody responseBody = response.body();
            assertTrue(responseBody != null, "Response body is null");

            String responseString = responseBody.string();
            JsonNode jsonResponse = objectMapper.readTree(responseString);

            LOGGER.info("Names | Emails:");
            for (JsonNode user : jsonResponse) {
                String name = user.get("name").asText();
                String email = user.get("email").asText();
                LOGGER.info(name + " | " + email);
            }

            String firstEmail = jsonResponse.get(0).get("email").asText();
            LOGGER.info("Checking if the first email contains '@'");

            assertTrue(firstEmail.contains("@"), "The email does not contain '@'");

        } catch (IOException e) {
            LOGGER.error("I/O Exception occurred during API request: {}", e.getMessage());
            fail("Test failed due to IOException: " + e.getMessage());
        }
    }
}