package com.github.alexk.demoguru99.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;
import com.github.alexk.utils.PageHelper;

public class DemoGuru99Page extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGuru99Page.class);

    private By iframeWarningTextLocator = By
            .xpath("//*[text()='iFrame will not show if you have adBlock extension enabled']");
    private By seleniumProjectTextLocator = By
            .xpath("//h1[@class='entry-title' and text()='Selenium Live Project for Practice']");
    private By emailInputLocator = By.xpath("//input[@id='philadelphia-field-email']");
    private By submitButtonLocator = By.xpath("//button[@id='philadelphia-field-submit']");
    private By seleniumDropdownLocator = By.xpath("//a[@class='dropdown-toggle' and contains(text(), 'Selenium')]");
    private By tooltipOptionLocator = By.xpath("//a[@href='../tooltip.html']");

    private String windowHandle;

    public DemoGuru99Page(WebDriver driver) {
        super(driver);
    }

    public DemoGuru99Page navigateTo(String url) {
        LOGGER.info("Navigating to url: {}", url);
        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String readyState = (String) js.executeScript("return document.readyState");
        if ("complete".equals(readyState)) {
            LOGGER.info("Page is fully loaded.");
        } else {
            LOGGER.info("Page is not fully loaded. Current state: " + readyState);
        }

        return this;
    }

    public DemoGuru99Page findIFrameAndClick() {
        LOGGER.info("Find iFrame warning and check its visibility");
        WebElement iFrameWarningElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(iframeWarningTextLocator));
        LOGGER.info("Scroll to iFrame warning");
        PageHelper.scrollToWebElement(driver, iFrameWarningElement);
        LOGGER.info("Find iFrame below iFrame warning");
        WebElement iframeElement = iFrameWarningElement.findElement(By.xpath("following-sibling::iframe"));
        driver.switchTo().frame(iframeElement);
        WebElement iframeBody = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        iframeBody.click();

        setWindowHandle(driver.getWindowHandle());

        return this;
    }

    public DemoGuru99Page switchToNewTab() {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(getWindowHandle())) {
                driver.switchTo().window(handle);
                break;
            }
        }

        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(seleniumProjectTextLocator));
        LOGGER.info("Live Selenium Project page is open and its title is visible: {}", titleElement.getText());

        return this;
    }

    public DemoGuru99Page switchToOriginalTab() {
        driver.switchTo().window(getWindowHandle());

        // switchTo().defaultContent() is not needed because switching to a new tab
        // automatically leaves the context
        // driver.switchTo().defaultContent();

        return this;
    }

    public DemoGuru99Page closeTab() {
        driver.close();
        LOGGER.info("Live Selenium Project tab is closed");
        return this;
    }

    public DemoGuru99Page enterEmail(String emailText) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
        emailInput.sendKeys(emailText);
        LOGGER.info("'{}' text is entered in the email input field", emailText);
        return this;
    }

    public DemoGuru99Page clickOnSubmit() {
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonLocator));
        submitButton.click();
        LOGGER.info("Clicked on Submit button");
        return this;
    }

    public boolean verifyAlert(String expectedText) throws NoAlertPresentException {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();

        alert.accept();

        return alertText.contains(expectedText);
    }

    public DemoGuru99Page navigateToTooltip() {
        WebElement seleniumDropdown = wait.until(ExpectedConditions.elementToBeClickable(seleniumDropdownLocator));
        seleniumDropdown.click();
        LOGGER.info("Selenium dropdown clicked");

        WebElement tooltipOption = wait.until(ExpectedConditions.elementToBeClickable(tooltipOptionLocator));
        tooltipOption.click();
        LOGGER.info("Tooltip option clicked");

        return this;
    }

    public boolean verifyDownloadLink() {
        WebElement downloadLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("download_now")));

        if (downloadLink.isDisplayed() && downloadLink.isEnabled()) {
            LOGGER.info("The 'Download now' link is visible and enabled");
            return true;
        }

        return false;
    }

    private String getWindowHandle() {
        return windowHandle;
    }

    private void setWindowHandle(String windowHandle) {
        this.windowHandle = windowHandle;
    }
}
