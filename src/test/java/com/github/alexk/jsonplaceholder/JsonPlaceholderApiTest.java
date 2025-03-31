package com.github.alexk.jsonplaceholder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;

//@Disabled
public class JsonPlaceholderApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPlaceholderApiTest.class);

    @Test
    public void testGetUsersAndVerifyFirstEmail() {
        String url = ConfigReader.getProperty("jsonplaceholder_baseurl");
        String path = "/users";

        try {
            String response = given()
                    .baseUri(url)
                    .when()
                    .get(path)
                    .then()
                    .statusCode(200)
                    .extract().asString();

            try {
                if (response == null || response.trim().isEmpty()) {
                    LOGGER.error("API response is empty or null, cannot parse JSON");
                    fail("API response is empty or null");
                }

                JsonPath jsonPath = new JsonPath(response);

                LOGGER.info("Names | Emails:");
                String name = "", email = "";

                for (int i = 0; i < jsonPath.getList("name").size(); i++) {
                    name = jsonPath.getString("name[" + i + "]");
                    email = jsonPath.getString("email[" + i + "]");
                    LOGGER.info(name + " | " + email);
                }

                String firstEmail = jsonPath.getString("email[0]");
                LOGGER.info("Checking if first email contains character: @");

                assertTrue(firstEmail.contains("@"), "The email does not contain '@'");

            } catch (JsonPathException | NullPointerException | IllegalArgumentException e) {
                LOGGER.error("Error while parsing JSON response: {}", e.getMessage());
                fail("JSON parsing failed");
            }
        } catch (Exception e) {
            LOGGER.error("HTTP request failed: {}", e.getMessage());
            fail("Failed to get a valid response from the API");
        }
    }
}
