package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class CartPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By backpackItem = By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']");
    private By fleeceJacketItem = By
            .xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Fleece Jacket']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyItemsInCart() {
        LOGGER.info("Verify items in cart");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(backpackItem)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOfElementLocated(fleeceJacketItem)).isDisplayed();
    }

    public CartPage proceedToCheckout() {
        LOGGER.info("Click checkout button");
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return this;
    }
}
