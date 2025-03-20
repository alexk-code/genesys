package com.github.alexk.demoguru99;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.CoreTest;
import com.github.alexk.demoguru99.pages.DemoGuru99Page;
import com.github.alexk.utils.PageHelper;

//@Disabled
public class DemoGuru99Test extends CoreTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGuru99Test.class);

    private String testEmail = "test@genesys.com";
    private String expectedAlertText = "Successfully";

    @Test
    public void testTextFormatting() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        LOGGER.info("pageLoadTimeout is increased to 5 mins as the page reaches readystate in 2-3 mins");
        DemoGuru99Page demoGuru99Page = new DemoGuru99Page(driver);

        LOGGER.info("Starting Guru99 test");

        String url = "https://demo.guru99.com/test/guru99home/";

        demoGuru99Page.navigateTo(url);
        LOGGER.info("Page was loaded in {}", PageHelper.getFormattedPageLoadTime(driver));

        demoGuru99Page.findIFrameAndClick().switchToNewTab().closeTab().switchToOriginalTab().enterEmail(testEmail)
                .clickOnSubmit();

        assertTrue(demoGuru99Page.verifyAlert(expectedAlertText),
                String.format("Alert message does NOT contain '%s'", expectedAlertText));
        
        demoGuru99Page.navigateToTooltip();

        assertTrue(demoGuru99Page.verifyDownloadLink(), "The 'Download now' link is NOT visible and NOT enabled.");
    }
}
