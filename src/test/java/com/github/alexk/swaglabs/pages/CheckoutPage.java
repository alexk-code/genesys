package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    // Locators
    private By firstNameField = By.cssSelector("[data-test='firstName']");
    private By lastNameField = By.cssSelector("[data-test='lastName']");
    private By postalCodeField = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By finishButton = By.cssSelector("[data-test='finish']");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Methods
    public CheckoutPage enterUserDetails(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return this;
    }

    public CheckoutPage completePurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        LOGGER.info("Continue button clicked");
        return this;
    }
}
