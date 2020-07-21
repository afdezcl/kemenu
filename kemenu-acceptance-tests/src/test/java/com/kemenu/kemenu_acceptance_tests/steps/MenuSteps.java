package com.kemenu.kemenu_acceptance_tests.steps;

import com.kemenu.kemenu_acceptance_tests.common.UserActions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

    @When("create a dish {string} on {string} section")
    public void createADishOnSection(String dishName, String sectionName) {
        UserActions.clickOn("//button[@type = 'submit' and contains(.//span, '" + sectionName + "')]");
        UserActions.clickOn("//button[@type = 'submit' and contains(@class, 'menu-digital-section-add-dish')]");

        WebElement dishNameInput = chromeTestRule.getChrome().findElementByXPath("//input[contains(@placeholder, 'Anchovies, potatoes, salad...')]");
        dishNameInput.sendKeys(dishName);
        WebElement dishDescription = chromeTestRule.getChrome().findElementByXPath("//input[contains(@placeholder, 'Dish description')]");
        dishDescription.sendKeys(dishName + "_description");
        WebElement dishPrice = chromeTestRule.getChrome().findElementByXPath("//input[@type = 'number' and @name = 'price']");
        dishPrice.sendKeys("9.99");
        UserActions.clickOn("//h5[contains(text(), 'Show allergens')]");
        UserActions.clickOn("(//i[contains(@class, 'cr-icon') and contains(@class, 'fa-check')])[4]");
        UserActions.clickOn("//input[@type = 'submit' and @value = 'Create dish']");
    }
}
