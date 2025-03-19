package com.github.alexk.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageHelper.class);

    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static boolean isScrolledToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long pageHeight = (Long) js.executeScript("return document.body.scrollHeight;");
        Long scrollPosition = (Long) js.executeScript("return window.scrollY + window.innerHeight;");
        LOGGER.info("PageHeight: {}, scrollPosition: {}", pageHeight, scrollPosition);
        return pageHeight.equals(scrollPosition);
    }
}