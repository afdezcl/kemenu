Feature: Register
  How a user register into the application

  Scenario: A user register an account
    Given A user visits REGISTER
    When fill the register form with "registerTest1@example.com" as email
    And clicks on SIGN_UP
    Then a "success" alert appears