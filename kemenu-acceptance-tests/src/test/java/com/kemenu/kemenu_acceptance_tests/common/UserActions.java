package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserActions {

    public static void clickOn(String xPath) {
        clickOn(xPath, 0);
    }

    private static void clickOn(String xPath, int cont) {
        try {
            WebElement buttonElement = chromeTestRule.getChrome().findElementByXPath(xPath);
            try {
                buttonElement.click();
            } catch (ElementNotInteractableException e) {
                chromeTestRule.getChrome().executeScript("arguments[0].click();", buttonElement);
            }
        } catch (StaleElementReferenceException e) {
            if (5 > cont) {
                clickOn(xPath, cont++);
            }
            throw e;
        }
    }

    public static void clickOnSection(String sectionName) {
        UserActions.clickOn("//button[@type = 'submit' and contains(.//span, '" + sectionName + "')]");
    }

    public static String getQRLink() {
        UserActions.clickOn("//button[@type = 'submit' and contains(text(), 'Get QR and share')]");
        new WebDriverWait(chromeTestRule.getChrome(), 2)
                .until(webDriver -> webDriver.findElement(By.xpath("//a[@target = '_blank' and contains(text(), 'https://kemenu.com/show/')]")).getText().contains("https://kemenu.com/show/"));
        String kemenuUrl = chromeTestRule.getChrome().findElementByXPath("//a[@target = '_blank' and contains(text(), 'https://kemenu.com/show/')]").getText();
        return kemenuUrl.replace("https://kemenu.com", "http://localhost:8085");
    }
}
