package com.github.alexk.onlinehtmleditor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.CoreTest;
import com.github.alexk.onlinehtmleditor.pages.OnlineHtmlEditorPage;
import com.github.alexk.utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class OnlineHtmlEditorTest extends CoreTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineHtmlEditorTest.class);

    @Test
    public void testTextFormatting() throws InterruptedException {
        OnlineHtmlEditorPage editorPage = new OnlineHtmlEditorPage(driver);

        LOGGER.info("Starting text formatting test");

        String url = ConfigReader.getProperty("onlinehtmleditor_baseurl");
        String text = "**Automation** *Test* Example";

        editorPage.navigateToUrl(url).enterText(text);

        String editorHtml = editorPage.getTextEditorContent();

        assertTrue(editorHtml.contains("<strong>Automation</strong>") &&
                editorHtml.contains("<i>Test</i>") &&
                editorHtml.contains("Example"), "Text does not match expected formatting.");
    }
}
