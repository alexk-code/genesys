package com.github.alexk.utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageHelper.class);

    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollToWebElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static boolean isScrolledToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long pageHeight = (Long) js.executeScript("return document.body.scrollHeight;");
        Long scrollPosition = (Long) js.executeScript("return window.scrollY + window.innerHeight;");
        LOGGER.info("PageHeight: {}, scrollPosition: {}", pageHeight, scrollPosition);
        return pageHeight.equals(scrollPosition);
    }

    public static Duration getPageLoadTime(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long loadTime = (Long) js.executeScript(
                "return window.performance.timing.loadEventEnd - window.performance.timing.navigationStart;");
        Duration duration = Duration.ofMillis(loadTime);
        return duration;
    }

    public static String getFormattedPageLoadTime(WebDriver driver) {
        Duration duration = getPageLoadTime(driver);

        long minutes = duration.toMinutes();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();
    
        return String.format("%d minutes, %d seconds, %d milliseconds", minutes, seconds, millis);
    }

    public static String getIsoFormattedPageLoadTime(Duration duration) {
        return duration.toString();
    }
}