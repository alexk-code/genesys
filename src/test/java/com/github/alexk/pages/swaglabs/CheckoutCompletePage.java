package com.github.alexk.pages.swaglabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BasePage;

public class CheckoutCompletePage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    private By orderSuccessMessage = By.cssSelector("[data-test='complete-header']");

    public CheckoutCompletePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean verifyOrderSuccess() {
        LOGGER.info("Verify success of order");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).getText()
                .equals("Thank you for your order!");
    }
}
