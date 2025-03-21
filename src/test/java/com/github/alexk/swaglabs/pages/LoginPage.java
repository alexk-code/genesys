package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class LoginPage extends BasePage  {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("[data-test='login-button']");
    private By dismissibleErrorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage navigateToUrl(String url) {
        LOGGER.info("Navigating to url: {}", url);
        driver.get(url);
        return this;
    }

    public LoginPage login(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        LOGGER.info("Login button clicked");
        return this;
    }

    public LoginPage enterCredentials(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

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