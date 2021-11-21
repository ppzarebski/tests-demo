package com.github.ppzarebski.qa.web.page.youtube;

import com.github.ppzarebski.qa.web.page.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.awaitility.Awaitility.await;

public class YTBasePage extends BasePage {

  public YTBasePage(WebDriver driver) {
    super(driver);
  }

  @FindBy(css = "input#search")
  public WebElement searchInput;

  @FindBy(css = "ytd-video-renderer")
  public List<WebElement> searchResults;

  @FindBy(css = "ytd-video-renderer a#video-title")
  public List<WebElement> searchResultLinks;

  @FindBy(css = "tp-yt-paper-dialog")
  public WebElement cookiesAskPrompt;

  @FindBy(css = "video.video-stream")
  public WebElement videoPlayer;

  public void removeCookiesDialogBox() {
    await().pollDelay(2, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).timeout(21, TimeUnit.SECONDS).untilAsserted(() -> {
      if (cookiesAskPrompt.isDisplayed()) {
        var jsExecutor = (JavascriptExecutor) this.driver;
        jsExecutor.executeScript("let box = document.querySelector('tp-yt-paper-dialog').parentElement; box.remove();");
      }
      assertWithMessage("Search input expect to be interactable").that(searchInput.isEnabled()).isTrue();
    });
  }
}
