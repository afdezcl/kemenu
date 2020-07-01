package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Button {
    SIGN_IN("btn btn-primary navbar-btn ml-0 ml-lg-3");

    private final String cssClass;
}
