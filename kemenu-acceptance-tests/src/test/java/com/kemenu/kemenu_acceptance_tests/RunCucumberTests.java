package com.kemenu.kemenu_acceptance_tests;

import com.kemenu.kemenu_acceptance_tests.common.ChromeTestRule;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
class RunCucumberTests {
    @ClassRule
    public static final ChromeTestRule chromeTestRule = new ChromeTestRule();
}