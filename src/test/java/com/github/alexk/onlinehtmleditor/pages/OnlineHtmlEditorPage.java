package com.github.alexk.onlinehtmleditor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OnlineHtmlEditorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By textEditor = By.cssSelector("[aria-label='Rich Text Editor. Editing area: main. Press Alt+0 for help.']");

    public OnlineHtmlEditorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void enterText(String text) {
        WebElement editor = wait.until(ExpectedConditions.visibilityOfElementLocated(textEditor));
        editor.click();
        editor.sendKeys(text);
    }

    public String getEditorContent() {
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(textEditor));
        return content.getDomProperty("innerHTML");
    }
}
