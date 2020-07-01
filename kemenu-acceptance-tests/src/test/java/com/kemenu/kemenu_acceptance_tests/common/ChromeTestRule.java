package com.kemenu.kemenu_acceptance_tests.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

@Getter
public class ChromeTestRule extends ExternalResource {

  private final ChromeDriver chrome;

  public ChromeTestRule() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setHeadless(true);
    chrome = new ChromeDriver(chromeOptions);
    chrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Override
  protected void after() {
    if (chrome != null) {
      chrome.close();
    }
  }
}
