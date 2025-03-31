package com.github.alexk.swaglabs.pages;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class CartPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By backpackItem = By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']");
    private By fleeceJacketItem = By
            .xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Fleece Jacket']");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean verifyItemsInCart() {
        LOGGER.info("Verify items in cart");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(backpackItem)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOfElementLocated(fleeceJacketItem)).isDisplayed();
    }

    public boolean verifyItemInCart(String itemName) {
        By itemSelector = By.xpath(getItemSelector(itemName));
        LOGGER.debug("Selector: {}", getItemSelector(itemName));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemSelector)).isDisplayed();
    }

    public boolean verifyItemsInCart(String... itemNames) {
        LOGGER.info("Verifying items in cart: {}", Arrays.toString(itemNames));

        for (String itemName : itemNames) {
            if (!verifyItemInCart(itemName)) {
                return false;
            }
        }

        return true;
    }

    public CheckoutPage proceedToCheckout() {
        LOGGER.info("Click checkout button");
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver, wait);
    }

    private String getItemSelector(String itemName) {
        return String.format("//div[@data-test='inventory-item-name' and text()='%s']", itemName);
    }
}
