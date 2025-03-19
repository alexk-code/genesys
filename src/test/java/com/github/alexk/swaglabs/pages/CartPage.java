package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By backpackItem = By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']");
    private By fleeceJacketItem = By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Fleece Jacket']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean verifyItemsInCart() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(backpackItem)).isDisplayed() &&
               wait.until(ExpectedConditions.visibilityOfElementLocated(fleeceJacketItem)).isDisplayed();
    }

    public CartPage proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        LOGGER.info("Checkout button clicked");
        return this;
    }
}