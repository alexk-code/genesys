package com.github.alexk.pages.swaglabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BasePage;
import com.github.alexk.utils.ConfigReader;

public class LoginPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("[data-test='login-button']");
    private By dismissibleErrorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public LoginPage navigateTo() {
        String url = ConfigReader.getProperty("swaglabs_baseurl");

        LOGGER.info("Navigating to url: {}", url);
        driver.get(url);
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        enterCredentials(username, password).clickOnLoginForSuccess();
        return new InventoryPage(driver, wait);
    }

    public InventoryPage clickOnLoginForSuccess() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        LOGGER.info("Login button clicked");
        return new InventoryPage(driver, wait);
    }

    public LoginPage enterCredentials(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public LoginPage clickOnLoginForFailure() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        LOGGER.info("Login button clicked");
        return this;
    }

    public boolean verifyErrorMessage(String expectedErrorMessage) {
        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(dismissibleErrorMessage))
                .getText();
        return expectedErrorMessage.equals(errorMessage);
    }
}