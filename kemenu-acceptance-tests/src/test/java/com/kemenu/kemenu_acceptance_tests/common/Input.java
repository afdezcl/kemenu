package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Input {
    EMAIL("//input[@type = 'email' and @name = 'email']"),
    PASSWORD("//input[@type = 'password' and @name = 'password']");

    private final String xPath;
}
