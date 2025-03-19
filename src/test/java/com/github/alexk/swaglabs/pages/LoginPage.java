package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("[data-test='login-button']");
    private By dismissibleErrorMessage = By.cssSelector("[data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Login Method
    public LoginPage login(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        LOGGER.info("Login button clicked");
        return this;
    }

    // Login Method
    public LoginPage enterCredentials(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    // Login Method
    public LoginPage clickOnLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        LOGGER.info("Login button clicked");
        return this;
    }

    public boolean verifyErrorMessage(String expectedErrorMessage) {
        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(dismissibleErrorMessage)).getText();
        return expectedErrorMessage.equals(errorMessage);
    }
}