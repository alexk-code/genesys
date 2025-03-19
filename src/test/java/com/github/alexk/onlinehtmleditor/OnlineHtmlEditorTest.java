package com.github.alexk.onlinehtmleditor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.CoreTest;
import com.github.alexk.swaglabs.SwaglabsTest;
import com.github.alexk.utils.ConfigReader;

import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@Disabled
public class OnlineHtmlEditorTest extends CoreTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaglabsTest.class);

    @Test
    public void testTextFormatting() throws InterruptedException {
        driver.get(ConfigReader.getProperty("onlinehtmleditor_baseurl"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ck-content")));

        editor.click();
        editor.sendKeys("**Automation** *Test* Example");

        // Get the editor content
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[aria-label='Rich Text Editor. Editing area: main. Press Alt+0 for help.']")));

        // Assert that the content includes the expected HTML structure
        String editorHtml = content.getDomProperty("innerHTML");

        LOGGER.info("editorHtml: {}", editorHtml);
        assertTrue(editorHtml.contains("<strong>Automation</strong>") &&
                editorHtml.contains("<i>Test</i>") &&
                editorHtml.contains("Example"), "Text does not match expected formatting.");
    }
}
