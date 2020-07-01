package com.kemenu.kemenu_acceptance_tests.steps;

import com.kemenu.kemenu_acceptance_tests.common.Button;
import com.kemenu.kemenu_acceptance_tests.common.WebPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

public class CommonSteps {

    @Given("A user visits {}")
    public void aUserVisits(WebPage webPage) {
        chromeTestRule.getChrome().get(webPage.url());
    }

    @When("clicks on {}")
    public void clicksOn(Button button) {
        WebElement buttonElement = chromeTestRule.getChrome().findElementByXPath(button.getXPath());
        try {
            buttonElement.click();
        } catch (ElementNotInteractableException e) {
            chromeTestRule.getChrome().executeScript("arguments[0].click();", buttonElement);
        }
    }

    @Then("is redirected to {string}")
    public void isRedirectedTo(String path) {
        new WebDriverWait(chromeTestRule.getChrome(), 1).until(webDriver -> webDriver.getCurrentUrl().endsWith(path));
    }
}
