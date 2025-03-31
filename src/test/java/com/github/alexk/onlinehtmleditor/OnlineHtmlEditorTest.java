package com.github.alexk.onlinehtmleditor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.BaseTest;
import com.github.alexk.onlinehtmleditor.pages.OnlineHtmlEditorPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

//@Disabled
public class OnlineHtmlEditorTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineHtmlEditorTest.class);

    private final String TEST_TEXT = "**Automation** *Test* Example";

    @Test
    public void testTextFormatting() throws InterruptedException {
        OnlineHtmlEditorPage editorPage = new OnlineHtmlEditorPage(getDriver(), getWait());

        LOGGER.info("Starting text formatting test");

        editorPage.navigateTo().enterText(TEST_TEXT);

        String editorHtml = editorPage.getTextEditorContent();

        assertTrue(editorHtml.contains("<strong>Automation</strong>") &&
                editorHtml.contains("<i>Test</i>") &&
                editorHtml.contains("Example"), "Text does not match expected formatting.");
    }
}
