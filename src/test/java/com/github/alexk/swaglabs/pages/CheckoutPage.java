package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class CheckoutPage  extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    private By firstNameField = By.cssSelector("[data-test='firstName']");
    private By lastNameField = By.cssSelector("[data-test='lastName']");
    private By postalCodeField = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By finishButton = By.cssSelector("[data-test='finish']");

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public CheckoutPage enterUserDetails(String firstName, String lastName, String postalCode) {
        LOGGER.info("Enter user credentials");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return this;
    }

    public CheckoutCompletePage completePurchase() {
        LOGGER.info("Click continue button");
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        return new CheckoutCompletePage(driver, wait);
    }
}
