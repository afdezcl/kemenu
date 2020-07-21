package com.kemenu.kemenu_acceptance_tests.steps;

import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;
import static com.kemenu.kemenu_acceptance_tests.common.Input.SECTION_NAME;

public class MenuSteps {

    @Then("puts {string} as section")
    public void fillSectionName(String sectionName) {
        WebElement sectionNameInput = chromeTestRule.getChrome().findElementByXPath(SECTION_NAME.getXPath());
        sectionNameInput.sendKeys(sectionName);
    }

    @Then("see the section {string} created")
    public void seeSection(String section) {
        chromeTestRule.getChrome().findElementByXPath("//div[contains(@class, 'menu-digital-section-title-text') and contains(.//span, '" + section + "')]").isDisplayed();
    }
}
