package com.kemenu.kemenu_acceptance_tests.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WebPage {
  HOME("/"),
  REGISTER("/register");

  private final String path;

  public String url() {
    return "http://localhost:8085" + path;
  }
}
