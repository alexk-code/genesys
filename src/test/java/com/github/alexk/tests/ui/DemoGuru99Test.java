package com.github.alexk.tests.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexk.base.BaseTest;
import com.github.alexk.pages.demoguru99.DemoGuru99Page;
import com.github.alexk.utils.DriverHelper;

//@Disabled
public class DemoGuru99Test extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGuru99Test.class);

    private final String TEST_EMAIL = "test@genesys.com";
    private final String EXPECTED_ALERT_TEXT = "Successfully";

    @Test
    public void testTextFormatting() {
        // pageLoadTimeout has to be increased to 5 mins becvause
        // page reaches readystate in 2-3 mins if page_load_strategy=eager
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        DemoGuru99Page demoGuru99Page = new DemoGuru99Page(getDriver(), getWait());

        LOGGER.info("Starting Guru99 test");

        String url = "https://demo.guru99.com/test/guru99home/";

        demoGuru99Page.navigateTo(url);
        LOGGER.debug("Page was loaded in {}", DriverHelper.getFormattedPageLoadTime(getDriver()));

        demoGuru99Page.findIFrameAndClick().switchToNewTab().closeTab().switchToOriginalTab().enterEmail(TEST_EMAIL)
                .clickOnSubmit();

        assertTrue(demoGuru99Page.verifyAlert(EXPECTED_ALERT_TEXT),
                String.format("Alert message does NOT contain '%s'", EXPECTED_ALERT_TEXT));

        demoGuru99Page.navigateToTooltip();

        assertTrue(demoGuru99Page.verifyDownloadLink(), "The 'Download now' link is NOT visible and NOT enabled.");
    }
}
