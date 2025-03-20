package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CheckoutCompletePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    private By orderSuccessMessage = By.cssSelector("[data-test='complete-header']");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean verifyOrderSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).getText().equals("Thank you for your order!");
    }
}
