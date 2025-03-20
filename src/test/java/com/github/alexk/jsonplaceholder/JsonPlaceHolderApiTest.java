package com.github.alexk.jsonplaceholder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.utils.ConfigReader;

import io.restassured.path.json.JsonPath;

//@Disabled
public class JsonPlaceHolderApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPlaceHolderApiTest.class);

    @Test
    public void testGetUsersAndVerifyFirstEmail() {
        String url = ConfigReader.getProperty("jsonplaceholder_baseurl");
        String path = "/users";

        String response = given()
                .baseUri(url)
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .extract().asString();

        JsonPath jsonPath = new JsonPath(response);

        LOGGER.info("Names | emails:");
        String name = "", email = "";

        for (int i = 0; i < jsonPath.getList("name").size(); i++) {
            name = jsonPath.getString("name[" + i + "]");
            email = jsonPath.getString("email[" + i + "]");
            LOGGER.info(name + " | " + email);
        }

        String firstEmail = jsonPath.getString("email[0]");
        
        LOGGER.info("Checking if first email contains character: @");
        assertTrue(firstEmail.contains("@"), "The email does not contain '@'");
    }
}
