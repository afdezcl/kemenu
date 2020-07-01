package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Button {
    SIGN_IN("//a[contains(@class,'navbar-btn') and text() = 'Sign in']"),
    SIGN_IN_SUBMIT("//input[@value = 'Sign in']");

    private final String xPath;
}
