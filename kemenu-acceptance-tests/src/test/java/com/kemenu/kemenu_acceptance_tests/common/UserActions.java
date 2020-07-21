package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserActions {

    public static void clickOn(String xPath) {
        WebElement buttonElement = chromeTestRule.getChrome().findElementByXPath(xPath);
        try {
            buttonElement.click();
        } catch (ElementNotInteractableException e) {
            chromeTestRule.getChrome().executeScript("arguments[0].click();", buttonElement);
        }
    }
}
