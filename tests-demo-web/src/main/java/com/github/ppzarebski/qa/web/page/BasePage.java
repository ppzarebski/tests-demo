package com.github.ppzarebski.qa.web.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {

  protected static Logger log = LoggerFactory.getLogger(BasePage.class);

  private static final int TIMEOUT = 10;
  private static final int POLLING = 10000;

  protected WebDriver driver;
  protected WebDriverWait driverWait;

  BasePage(WebDriver driver) {
    this.driver = driver;
    driverWait = new WebDriverWait(driver, TIMEOUT, POLLING);
    PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    log.info("Driver set up.");
  }

  protected void waitForElementToAppear(WebElement webElement) {
    driverWait.until(ExpectedConditions.visibilityOf(webElement));
    log.info("Element {} found.", webElement.getTagName());
  }
}
