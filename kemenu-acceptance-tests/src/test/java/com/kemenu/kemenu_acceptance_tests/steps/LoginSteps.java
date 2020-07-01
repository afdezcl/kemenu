package com.kemenu.kemenu_acceptance_tests.steps;

import com.kemenu.kemenu_acceptance_tests.common.Input;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebElement;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;

public class LoginSteps {

    @And("puts {string} as {} and {string} as {} in login form")
    public void clicksOn(String email, Input emailInput, String password, Input passwordInput) {
        WebElement emailInputField = chromeTestRule.getChrome().findElementByXPath(emailInput.getXPath());
        WebElement passwordInputField = chromeTestRule.getChrome().findElementByXPath(passwordInput.getXPath());

        emailInputField.sendKeys(email);
        passwordInputField.sendKeys(password);
    }
}
