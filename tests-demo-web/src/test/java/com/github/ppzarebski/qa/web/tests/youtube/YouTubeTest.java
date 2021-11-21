package com.github.ppzarebski.qa.web.tests.youtube;

import com.github.ppzarebski.qa.commons.test.BaseSuite;
import com.github.ppzarebski.qa.web.driver.BrowserName;
import com.github.ppzarebski.qa.web.driver.WebDriverFactory;
import com.github.ppzarebski.qa.web.page.youtube.YTBasePage;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;
import static org.awaitility.Awaitility.await;

public class YouTubeTest extends BaseSuite {

  private static final Logger log = LoggerFactory.getLogger(YouTubeTest.class);

  private static final String SEARCH_TEXT = "led zeppelin.bron-yr-aur";
  private WebDriver driver;

  @AfterEach
  void afterEach() {
    if (driver != null) {
      driver.close();
    }
  }

  @DisplayName("should play youtube content")
  @Test
  void runYtTest() {
    _given("youtube main page");
    driver = WebDriverFactory.driver(BrowserName.CHROME, "https://www.youtube.com");
    var ytPage = new YTBasePage(driver);
    ytPage.removeCookiesDialogBox();
    ytPage.waitForElementToAppear(ytPage.searchInput);

    _when("search for: %s is requested", SEARCH_TEXT);
    ytPage.searchInput.sendKeys(SEARCH_TEXT);
    ytPage.searchInput.sendKeys(Keys.ENTER);

    _and("proper result is choosed");
    ytPage.searchResultLinks.stream().filter(element ->
                element.getAttribute("title").toLowerCase().contains(StringUtils.substringAfter(SEARCH_TEXT, ".")))
          .findFirst().orElseThrow().click();

    _then("video should be started automatically");
    //TODO: proper assertion for video playing
    await().pollDelay(11, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).timeout(20, TimeUnit.SECONDS).untilAsserted(() -> {
      log.info("Hope it worked, now you can listen to some good music :)");
      assertThat(ytPage.videoPlayer.isEnabled()).isTrue();
    });
  }
}
