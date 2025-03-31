package com.github.alexk.tests.ui;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BaseTest;
import com.github.alexk.pages.swaglabs.CartPage;
import com.github.alexk.pages.swaglabs.CheckoutCompletePage;
import com.github.alexk.pages.swaglabs.CheckoutPage;
import com.github.alexk.pages.swaglabs.InventoryPage;
import com.github.alexk.pages.swaglabs.LoginPage;
import com.github.alexk.utils.ConfigReader;
import com.github.alexk.utils.DriverHelper;
import com.github.alexk.utils.TestHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

//@Disabled
public class SwaglabsTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaglabsTest.class);

    private final String backpackItem = "Sauce Labs Backpack";
    private final String fleeceJacketItem  = "Sauce Labs Fleece Jacket";

    @Test
    public void testCompleteCheckout() {
        String testName = "Complete Checkout Test";
        TestHelper.testInfo(true, testName);

        LoginPage loginPage = new LoginPage(getDriver(), getWait());
        
        String username = ConfigReader.getProperty("user_performance.username");
        String password = ConfigReader.getProperty("user_performance.password");

        InventoryPage inventoryPage = loginPage.navigateTo().loginAs(username, password);
        assertTrue(inventoryPage.isLoaded(), "Inventory page not loaded properly!");

        inventoryPage.addItemsToCart(backpackItem, fleeceJacketItem);
        assertTrue(inventoryPage.verifyCartCount("2"), "Cart count verification failed!");

        CartPage cartPage = inventoryPage.openCart();
        assertTrue(cartPage.verifyItemsInCart(backpackItem, fleeceJacketItem), "Items in cart verification failed!");

        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        CheckoutCompletePage checkoutCompletePage = checkoutPage.enterUserDetails("Gene", "sys", "1111").completePurchase();
        assertTrue(checkoutCompletePage.verifyOrderSuccess());
        
        TestHelper.testInfo(false, testName);
    }

    @Test
    public void testErrorMessages() {
        String testName = "Error Messages Test";
        TestHelper.testInfo(true, testName);

        LoginPage loginPage = new LoginPage(getDriver(), getWait()).navigateTo().clickOnLoginForFailure();
        assertTrue(loginPage.verifyErrorMessage("Epic sadface: Username is required"),
                "The expected error message is not found!");

        String username = ConfigReader.getProperty("user_standard.username");
        String password = ConfigReader.getProperty("user_standard.password");

        InventoryPage inventoryPage = loginPage.enterCredentials(username, password).clickOnLoginForSuccess();
        assertTrue(inventoryPage.isLoaded(), "Inventory page not loaded properly!");

        DriverHelper.scrollToBottom(getDriver());
        assertTrue(DriverHelper.isScrolledToBottom(getDriver()), "Page did not scroll to the bottom!");
        assertTrue(inventoryPage.verifyFooterText("2025", "Terms of Service"));

        TestHelper.testInfo(false, testName);
    }
}