package com.kemenu.kemenu_acceptance_tests.steps;

import io.cucumber.java.en.And;
import org.openqa.selenium.WebElement;

import static com.kemenu.kemenu_acceptance_tests.RunCucumberTests.chromeTestRule;
import static com.kemenu.kemenu_acceptance_tests.common.Input.BUSINESS_NAME;
import static com.kemenu.kemenu_acceptance_tests.common.Input.CONFIRM_PASSWORD;
import static com.kemenu.kemenu_acceptance_tests.common.Input.PASSWORD;
import static com.kemenu.kemenu_acceptance_tests.common.Input.REGISTER_EMAIL;

public class RegisterSteps {

    @And("fill the register form with {string} as email")
    public void fillRegisterForm(String email) {
        WebElement businessNameInputField = chromeTestRule.getChrome().findElementByXPath(BUSINESS_NAME.getXPath());
        WebElement emailInputField = chromeTestRule.getChrome().findElementByXPath(REGISTER_EMAIL.getXPath());
        WebElement passwordInputField = chromeTestRule.getChrome().findElementByXPath(PASSWORD.getXPath());
        WebElement confirmPasswordInputField = chromeTestRule.getChrome().findElementByXPath(CONFIRM_PASSWORD.getXPath());

        businessNameInputField.sendKeys("Test Restaurant");
        emailInputField.sendKeys(email);
        passwordInputField.sendKeys("testPassword");
        confirmPasswordInputField.sendKeys("testPassword");
    }
}
