package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Button {
    SIGN_IN("//a[contains(@class,'navbar-btn') and text() = 'Sign in']"),
    SIGN_IN_SUBMIT("//input[@value = 'Sign in']"),
    SIGN_UP("//button[contains(., 'Sign up')]"),
    FIRST_MENU_BUTTON("(//div[contains(@class, 'first-menu-button')])[2]"),
    CREATE_SECTION("//input[@type = 'submit' and @value = 'Section']"),
    DIGITAL_MENU_COLLAPSE("//button[@type = 'submit' and contains(@class, 'menu-digital-title')]");

    private final String xPath;
}
