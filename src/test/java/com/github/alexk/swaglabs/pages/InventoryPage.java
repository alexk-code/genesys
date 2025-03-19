package com.github.alexk.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryPage.class);
    private final String TITLE = "Products";

    private By backpack = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private By fleeceJacket = By.cssSelector("[data-test='add-to-cart-sauce-labs-fleece-jacket']");
    private By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private By title = By.cssSelector("[data-test='title']");
    private By footer = By.cssSelector("div.footer_copy[data-test='footer-copy']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public InventoryPage addBackpackToCart() {
        LOGGER.info("Adding backpack to cart");
        wait.until(ExpectedConditions.elementToBeClickable(backpack)).click();
        return this;
    }

    public InventoryPage addFleeceJacketToCart() {
        LOGGER.info("Adding fleece jacket to cart");
        wait.until(ExpectedConditions.elementToBeClickable(fleeceJacket)).click();
        return this;
    }

    public InventoryPage openCart() {
        LOGGER.info("Opening shopping cart");
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return this;
    }
}
