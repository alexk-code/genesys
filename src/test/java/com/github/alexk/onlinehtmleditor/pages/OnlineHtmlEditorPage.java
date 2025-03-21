package com.github.alexk.onlinehtmleditor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BasePage;

public class OnlineHtmlEditorPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineHtmlEditorPage.class);

    private By textEditor = By.cssSelector("[aria-label='Rich Text Editor. Editing area: main. Press Alt+0 for help.']");

    public OnlineHtmlEditorPage(WebDriver driver) {
        super(driver);
    }

    public OnlineHtmlEditorPage navigateToUrl(String url) {
        LOGGER.info("Navigate to url: {}", url);
        driver.get(url);
        return this;
    }

    public OnlineHtmlEditorPage enterText(String text) {
        LOGGER.info("Enter text: {}", text);
        WebElement editor = wait.until(ExpectedConditions.visibilityOfElementLocated(textEditor));
        editor.click();
        editor.sendKeys(text);
        return this;
    }

    public String getTextEditorContent() {
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(textEditor));
        return content.getDomProperty("innerHTML");
    }
}
