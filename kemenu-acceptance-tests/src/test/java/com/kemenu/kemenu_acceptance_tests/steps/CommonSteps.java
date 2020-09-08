package com.kemenu.kemenu_acceptance_tests.steps;

import com.kemenu.kemenu_acceptance_tests.common.Button;
import com.kemenu.kemenu_acceptance_tests.common.UserActions;
import com.kemenu.kemenu_acceptance_tests.common.WebPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

public class CommonSteps {

    @Given("A user visits {}")
    public void aUserVisits(WebPage webPage) {
        chromeTestRule.getChrome().get(webPage.url());
    }

    @When("clicks on {}")
    public void clicksOn(Button button) {
        UserActions.clickOn(button.getXPath());
    }

    @Then("is redirected to {string}")
    public void isRedirectedTo(String path) {
        new WebDriverWait(chromeTestRule.getChrome(), 1).until(webDriver -> webDriver.getCurrentUrl().endsWith(path));
    }

    @Then("a {string} alert appears")
    public void anAlertAppears(String alertType) {
        chromeTestRule.getChrome().findElementByXPath("//div[contains(@class, 'alert-" + alertType + "')]").isDisplayed();
    }

    @Then("a {string} growl appears")
    public void aGrowlAppears(String growlType) {
        chromeTestRule.getChrome().findElementByXPath("//div[contains(@class, 'toast-" + growlType + "')]").isDisplayed();
    }
}
