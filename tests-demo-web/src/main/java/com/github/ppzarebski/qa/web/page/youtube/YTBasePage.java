package com.github.ppzarebski.qa.web.page.youtube;

import com.github.ppzarebski.qa.web.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
}
