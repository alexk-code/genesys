package com.github.alexk.pages.swaglabs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BasePage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryPage.class);

    private final String TITLE = "Products";

    private By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private By title = By.cssSelector("[data-test='title']");
    private By footer = By.cssSelector("[data-test='footer-copy']");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean verifyCartCount(String expectedCount) {
        String actualCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        LOGGER.info("Cart count: Expected = {}, Actual = {}", expectedCount, actualCount);
        return actualCount.equals(expectedCount);
    }

    public boolean verifyFooterText(String expectedText) {
        String footerText = wait.until(ExpectedConditions.visibilityOfElementLocated(footer)).getText();
        LOGGER.info("Footer message: {}", footerText);
        return expectedText.equals(footerText);
    }

    public boolean verifyFooterText(String... expectedTexts) {
        String footerText = wait.until(ExpectedConditions.visibilityOfElementLocated(footer)).getText().trim();
        LOGGER.info("Footer message: {}", footerText);

        List<String> missingTexts = Arrays.stream(expectedTexts)
                .filter(expected -> !footerText.contains(expected))
                .collect(Collectors.toList());

        if (!missingTexts.isEmpty()) {
            LOGGER.error("Footer does not contain expected texts: {}", missingTexts);
            return false;
        }

        return true;
    }

    public boolean isLoaded() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String readyState = js.executeScript("return document.readyState").toString();
                return "complete".equals(readyState);
            }
        };

        boolean isPageLoaded = wait.until(pageLoadCondition);

        if (!isPageLoaded) {
            return false;
        }

        String titleText = wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
        return TITLE.equals(titleText);
    }

    public InventoryPage addItemToCart(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            LOGGER.warn("Item name is invalid: {}", itemName);
            return this;
        }

        By item = By.cssSelector(getItemSelector(itemName));
        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
        return this;
    }

    public InventoryPage addItemsToCart(String... itemNames) {
        for (String itemName : itemNames) {
            addItemToCart(itemName);
        }
        return this;
    }

    public CartPage openCart() {
        LOGGER.info("Open shopping cart");
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver, wait);
    }

    private String getItemSelector(String itemName) {
        return String.format("[data-test='add-to-cart-%s']", itemAsAttribute(itemName));
    }

    
    private String itemAsAttribute(String itemName) {
        return itemName.trim()
                .toLowerCase()
                .replaceAll("\\s+", "-");
    }
}
