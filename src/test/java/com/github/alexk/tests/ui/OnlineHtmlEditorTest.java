package com.github.alexk.tests.ui;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BaseTest;
import com.github.alexk.pages.onlinehtmleditor.OnlineHtmlEditorPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

//@Disabled
public class OnlineHtmlEditorTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineHtmlEditorTest.class);

    private final String testText = "**Automation** *Test* Example";

    @Test
    public void testTextFormatting() throws InterruptedException {
        OnlineHtmlEditorPage editorPage = new OnlineHtmlEditorPage(getDriver(), getWait());

        LOGGER.info("Starting text formatting test");

        editorPage.navigateTo().enterText(testText);

        String editorHtml = editorPage.getTextEditorContent();

        assertTrue(editorHtml.contains("<strong>Automation</strong>") &&
                editorHtml.contains("<i>Test</i>") &&
                editorHtml.contains("Example"), "Text does not match expected formatting.");
    }
}
