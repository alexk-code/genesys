package com.github.alexk.pages.onlinehtmleditor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BasePage;
import com.github.alexk.utils.ConfigReader;

public class OnlineHtmlEditorPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineHtmlEditorPage.class);

    private By textEditor = By.cssSelector("[aria-label^='Rich Text Editor']");

    public OnlineHtmlEditorPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public OnlineHtmlEditorPage navigateTo() {
        String url = ConfigReader.getProperty("onlinehtmleditor_baseurl");

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
