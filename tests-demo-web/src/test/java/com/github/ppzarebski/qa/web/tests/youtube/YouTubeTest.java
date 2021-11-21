package com.github.ppzarebski.qa.web.tests.youtube;

import com.github.ppzarebski.qa.commons.test.BaseSuite;
import com.github.ppzarebski.qa.web.driver.BrowserName;
import com.github.ppzarebski.qa.web.driver.WebDriverFactory;
import com.github.ppzarebski.qa.web.page.youtube.YTBasePage;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class YouTubeTest extends BaseSuite {

  private static final String SEARCH_TEXT = "led zeppelin.bron-yr-aur";

  @DisplayName("should play youtube content")
  @Test
  void runYtTest() {
    _given("youtube main page");
    var driver = WebDriverFactory.driver(BrowserName.CHROME, "https://www.youtube.com");
    var ytPage = new YTBasePage(driver);
    ytPage.waitForElementToAppear(ytPage.searchInput);

    _when("search for: %s is requested", SEARCH_TEXT);
    ytPage.searchInput.sendKeys(SEARCH_TEXT);
    ytPage.searchInput.sendKeys(Keys.ENTER);

    _and("proper result is choosed");
    ytPage.searchResultLinks.stream().filter(element ->
                element.getCssValue("title").toLowerCase().contains(StringUtils.substringBefore(SEARCH_TEXT, "."))
                      && element.getCssValue("title").toLowerCase().contains(StringUtils.substringAfter(SEARCH_TEXT, ".")))
          .findFirst().orElseThrow().click();
//    await().pollInterval(1, TimeUnit.SECONDS).timeout(20, TimeUnit.SECONDS).untilAsserted(() -> {
//      assertThat(ytPage)
//    });
  }
}
