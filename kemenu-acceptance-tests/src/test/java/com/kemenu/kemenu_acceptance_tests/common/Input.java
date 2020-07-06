package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Input {
    EMAIL("//input[@type = 'email' and @name = 'email']"),
    PASSWORD("//input[@type = 'password']"),
    BUSINESS_NAME("//input[@id = 'businessName']"),
    REGISTER_EMAIL("//input[@id = 'email']"),
    CONFIRM_PASSWORD("//input[@id = 'confirmPassword']");

    private final String xPath;
}
