package com.kemenu.kemenu_acceptance_tests.steps;

import com.kemenu.kemenu_acceptance_tests.common.Button;
import com.kemenu.kemenu_acceptance_tests.common.WebPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

public class CommonSteps {

    @Given("A user visits {}")
    public void aUserVisits(WebPage webPage) {
        chromeTestRule.getChrome().get(webPage.url());
    }

    @When("clicks on {}")
    public void clicksOn(Button button) {
        WebElement signInButton = chromeTestRule.getChrome().findElementByClassName(button.getCssClass());
        System.out.println("ad");
    }
}
