package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class CheckoutCompletePage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    private By orderSuccessMessage = By.cssSelector("[data-test='complete-header']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyOrderSuccess() {
        LOGGER.info("Verify success of order");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).getText()
                .equals("Thank you for your order!");
    }
}
