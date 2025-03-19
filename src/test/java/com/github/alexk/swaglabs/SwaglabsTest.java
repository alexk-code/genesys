package com.github.alexk.swaglabs;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.CoreTest;
import com.github.alexk.swaglabs.pages.CartPage;
import com.github.alexk.swaglabs.pages.CheckoutCompletePage;
import com.github.alexk.swaglabs.pages.CheckoutPage;
import com.github.alexk.swaglabs.pages.InventoryPage;
import com.github.alexk.swaglabs.pages.LoginPage;
import com.github.alexk.utils.ConfigReader;
import com.github.alexk.utils.PageHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

@Disabled
public class SwaglabsTest extends CoreTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaglabsTest.class);

    @Test
    public void testCompleteCheckout() {
        driver.get(ConfigReader.getProperty("swaglabs_baseurl") + "inventory.html");
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);

        LOGGER.info("Starting checkout test");
        loginPage
                .enterCredentials(ConfigReader.getProperty("user_performance.username"),
                        ConfigReader.getProperty("user_performance.password"))
                .clickOnLogin();
        assertTrue(inventoryPage.isLoaded(), "Inventory page not loaded properly!");

        inventoryPage.addBackpackToCart()
                .addFleeceJacketToCart()
                .openCart();
        assertTrue(cartPage.verifyItemsInCart(), "Cart verification failed!");
        cartPage.proceedToCheckout();
        checkoutPage.enterUserDetails("Gene", "sys", "1111")
                .completePurchase();
        assertTrue(checkoutCompletePage.verifyOrderSuccess());
        LOGGER.info("Checkout test completed successfully");
    }

    @Test
    public void testErrorMessages() {
        driver.get(ConfigReader.getProperty("swaglabs_baseurl") + "inventory.html");
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        LOGGER.info("Starting error messages test");
        loginPage.clickOnLogin();
        assertTrue(loginPage.verifyErrorMessage("Epic sadface: Username is required"),
                "The expected error message is not found!");
        loginPage.enterCredentials(ConfigReader.getProperty("user_standard.username"),
                ConfigReader.getProperty("user_standard.password")).clickOnLogin();
        assertTrue(inventoryPage.isLoaded(), "Inventory page not loaded properly!");
        PageHelper.scrollToBottom(driver);
        assertTrue(PageHelper.isScrolledToBottom(driver), "Page did not scroll to the bottom!");
        assertTrue(inventoryPage.verifyFooterText("2025", "Terms of Service"));
    }
}