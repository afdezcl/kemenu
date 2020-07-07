Feature: Register
  How a user register into the application

  Scenario: A user register an account
    Given A user visits REGISTER
    When fill the register form
    And clicks on SIGN_UP
    Then a "success" alert appears